package com.example.goodsafe

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import net.daum.mf.map.api.MapView
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapView = MapView(this)
        map_view.addView(mapView)
    }

}