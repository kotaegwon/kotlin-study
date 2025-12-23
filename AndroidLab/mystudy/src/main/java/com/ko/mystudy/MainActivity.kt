package com.ko.mystudy

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ko.mystudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mMainBinding: ActivityMainBinding

    companion object {
        private val TAG: String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate+")

        mMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mMainBinding.root)
        setSupportActionBar(mMainBinding.toolbar)

        Log.i(TAG, "onCreate+")
    }

    // 액티비티에 정적인 메뉴를 구성할 때 사용
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.i(TAG, "onCreateOptionsMenu+")
        val menuItem1: MenuItem? = menu?.add(
            /* groupId = */ 0,
            /* itemId = */ 0,
            /* order = */ 0,
            /* title = */ "menu1"
        )

        val menuItem2: MenuItem? = menu?.add(
            /* groupId = */ 0,
            /* itemId = */ 1,
            /* order = */ 0,
            /* title = */ "menu2"
        )

        return super.onCreateOptionsMenu(menu)
        Log.i(TAG, "onCreateOptionsMenu-")
    }

    // onCreateOptionMenu 메뉴 이벤트 처리 함수
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i(TAG, "onOptionsItemSelected+")

        Log.i(TAG, "onOptionsItemSelected : item = $item")
        return super.onOptionsItemSelected(item)

        Log.i(TAG, "onOptionsItemSelected-")
    }

    // 메뉴가 화면에 나올 때마다 동적으로 구성하고 싶은 경우에 선택
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Log.i(TAG, "onPrepareOptionsMenu+")

        Log.i(TAG, "onPrepareOptionsMenu : menu = $menu")
        return super.onPrepareOptionsMenu(menu)
        Log.i(TAG, "onPrepareOptionsMenu-")
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy+")

        super.onDestroy()

        Log.i(TAG, "onDestroy-")
    }
}