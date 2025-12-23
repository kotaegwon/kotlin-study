package com.example.ch7_layout

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ch7_layout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mMainBinding: ActivityMainBinding

    companion object {
        private var TAG: String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var mClickListener: ClickListener

        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate+")

        mMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mMainBinding.root)

        mClickListener = ClickListener()

        listOf(
            mMainBinding.tvNum0, mMainBinding.tvNum1, mMainBinding.tvNum2,
            mMainBinding.tvNum3, mMainBinding.tvNum4, mMainBinding.tvNum5,
            mMainBinding.tvNum6, mMainBinding.tvNum7, mMainBinding.tvNum8,
            mMainBinding.tvNum9, mMainBinding.tvStar, mMainBinding.tvSign
        ).forEach { it.setOnClickListener(mClickListener) }

        mMainBinding.imgDelete.setOnClickListener {
            deleteLastNum()
        }

        Log.i(TAG, "onCreate-")
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy+")

        super.onDestroy()

        Log.i(TAG, "onDestroy-")
    }

    fun addNum(num: String) {
        mMainBinding.tvInputNum.append(num)
    }

    fun deleteLastNum() {
        val currentText = mMainBinding.tvInputNum.text.toString()
        if (currentText.isNotEmpty()) {
            mMainBinding.tvInputNum.text = currentText.dropLast(1)
        }
    }

    inner class ClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            if (v is TextView) {
                val text = v.text.toString()
                Log.i(TAG, "ClickListener get text = $text")
                addNum(text)
            }
        }
    }
}