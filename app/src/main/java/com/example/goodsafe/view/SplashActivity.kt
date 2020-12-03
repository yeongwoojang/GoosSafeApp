package com.example.goodsafe.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.goodsafe.R
import dagger.hilt.android.qualifiers.ApplicationContext

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //2초동안 스플래시 화면을 보여준다.
        Handler(Looper.getMainLooper()).postDelayed({
            val intent  = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }

    //스플래시 화면 중 뒤로가기 버튼 금지
    override fun onBackPressed() {
    }
}