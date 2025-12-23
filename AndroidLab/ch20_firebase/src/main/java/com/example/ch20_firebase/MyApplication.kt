package com.example.ch20_firebase

import androidx.multidex.MultiDexApplication
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

/*
 * 파이어 베이스
 * 안드로이드 앱에서 서버리스 컴퓨팅을 구현할 수 있음
 *
 * 핵심 기능
 * Authentication: 인증, 회원가입 및 로그인 처리
 * Cloud Message: 알림 전송
 * Firebase Database: 앱 데이터 저장 및 동기화
 * Realtime Database: 실시간 데이터 저장 및 동기화
 * Storage: 파일 저장소
 * Hosting: 웹 호스팅
 * Functions: 서버 관리 없이 모바일 백엔드 코드 실행
 * Machine Learning: 모바일 개발자용 머신러닝
 */

class MyApplication : MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth
        lateinit var db: FirebaseFirestore
        lateinit var storage: FirebaseStorage
        var email: String? = null
        fun checkAuth(): Boolean {
            val currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                if (currentUser.isEmailVerified) {
                    true
                } else {
                    false
                }
            } ?: let {
                false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage
    }
}