package com.example.ch6_view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ch6_view.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    /* 
    자바의 private static final
    코틀린에는 static 키워드가 없음
    그래서 클래스 내부에서 "클래스 단위로 공유되는 변수나 함수"를 만들 때 companion object(동반객체)사용
    클래스 안에 딱 하나만 존재할 수 있고 클래스 이름을 통해 접근 가능

     */
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var mMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate +")
        super.onCreate(savedInstanceState)

        mMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mMainBinding.root)

        mMainBinding.btnOkay.setOnClickListener {
            Toast.makeText(this, "onCreate : clicked okay button", Toast.LENGTH_SHORT).show()
        }

        Log.i(TAG, "onCreate -")
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy+")
        super.onDestroy()
        Log.i(TAG, "onDestroy-")
    }

}