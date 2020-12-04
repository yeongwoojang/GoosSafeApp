package com.example.goodsafe.view.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goodsafe.R
import kotlinx.android.synthetic.main.activity_map.*
import net.daum.mf.map.gen.KakaoMapLibraryAndroidMeta

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
//        val url = "daummaps://route?sp="+USER Latitude+","+USER Longitude+"&ep="+ARRIVAL Latitude+","+ARRIVAL Longitude+"&by=FOOT";
        val kakaoUrl = "kakaomap://route?sp=37.537229,127.005515&ep=37.48427478,126.9323604&by=CAR"

        bt.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(kakaoUrl))


            startActivity(intent);

        }
    }
}