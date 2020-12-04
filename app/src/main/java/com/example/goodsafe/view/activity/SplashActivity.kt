package com.example.goodsafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.goodsafe.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //2초동안 스플래시 화면을 보여준다.
        Handler(Looper.getMainLooper()).postDelayed({
            val intent  = Intent(this,
                BottomNavActivity::class.java)
            startActivity(intent)
            finish()
        },1000)
    }

    //스플래시 화면 중 뒤로가기 버튼 금지
    override fun onBackPressed() {
    }
}