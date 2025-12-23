package com.example.ch13_activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.ch13_activity.databinding.ActivityAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.time.measureTime

/*
ANR(Activity not response)
액티비티가 응답하지 않는 오류 상황
액티비티를 작성할 때 ANR을 고려하지 않으면 앱이 수시로 종료될 수 있음

시스템에서 액티비티를 실행하는 수행 흐름을 메인 스레드 또는 UI 스레드라고 함

ANR 문제를 해결하는 방법은 액티비티를 실행한 메인 스레드 이외에 실행 흐름(개발자 스레드)을
따로 만들어서 시간이 오래 걸리는 작업을 담당하게 하면 됨.
하지만 위 방법은 ANR오류는 해결되지만 화면을 변경할 수 없다는 또 다른 문제가 생김
왜나하면 화면 변경은 개발자가 만든 스레드에서는 할 수 없고 액티비티를 출력한 메인 스레드에서만 할 수 있기 때문
 */

/*
코루틴으로 ANR 오류 해결
코루틴은 한마디로 비동기 경랑 스레드(non-blocking lightweight thread)라고 요악할 수 있음
 */
class AddActivity : AppCompatActivity() {
    companion object {
        private var TAG: String = "AddActivity"
    }

    lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        /*
        코루틴을 구동하려면 먼저 스코프를 준비해야 함.
        그리고 스코프에서 코루틴을 구동. 스코프는 성격이 같은 코루틴을 묶는 개념으로 이해
        한 스코프에 여러 코루틴을 구동할 수 있으며 한 애플리케이션에 여러 스코프를 만들 수 있음
        결국 스코프는 성격이 같은 여러 코루틴이 동작하는 공간으로 이해

        디스패처는 이 스코프에서 구동한 코루틴이 어디에서 동작해야 하는지를 나타냄
        backgroundScope에는 Dispatcher.Default를 지정했으면 mainScope에는 Dispatchers.Main을 저장
        
        Dispatchers.Main: 액티비티의 메인 스레드에서 동작하는 코루틴을 만듬
        Dispatchers.IO: 파일에 읽거나 쓰기 또는 네트워크 작업 등에 최적화되어있음
        Dispatchers.Default: CPU를 많이 사용하는 직업을 백그라운드에서 실행
        
        Channel은 큐 알고리즘과 비슷하며 Channel의 send() 함수로 데이터를 전달하면 그 데이터를 받는 코루틴에서는
        receive()나 consumeEach()등의 함수로 데이터를 받음
         */
        val channel = Channel<Int>()
        val backGroundScope = CoroutineScope(Dispatchers.Default + Job());

        backGroundScope.launch {
            var sum = 0L
            var time = measureTime {
                for (i in 1..2_000_000_000) {
                    sum += i
                }
            }
            Log.d(TAG, "time : $time")
            channel.send(sum.toInt());
        }

        val mainScope = GlobalScope.launch(Dispatchers.Main) {
            channel.consumeEach { binding.tvTest.text = "sum : $it" }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //add............................
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_add_save -> {
            val intent = intent
            intent.putExtra("result", binding.addEditView.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
            true
        }

        else -> true
    }

}