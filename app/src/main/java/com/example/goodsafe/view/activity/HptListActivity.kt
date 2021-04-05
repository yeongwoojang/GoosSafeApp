package com.example.goodsafe.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goodsafe.R
import com.example.goodsafe.view.adapter.ListAdapter
import com.example.goodsafe.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HptListActivity : AppCompatActivity() {

    companion object{
        val FINISH_INTERVAL_TIME :Long=2000
        var backPressedTime :Long=0
    }

    val viewModel by viewModels<HomeViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hpt_list)

        val adapter = ListAdapter(this)
        viewModel.updateLocation()
        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@HptListActivity, RecyclerView.VERTICAL,false)
            this.adapter = adapter
        }
        viewModel.point.observe(this, Observer {point->
            if(point!=null){
                viewModel.getNearHostpital(point.latitude, point.longitude)
            }else{
                viewModel.updateLocation()
            }
        })
        viewModel.nearHospital.observe(this, Observer {
            adapter.updateItems(it)
        })
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(this,HomeActivity::class.java))
//        var tempTime = System.currentTimeMillis()
//        var intervalTime = tempTime- backPressedTime
//        if(0<=intervalTime && FINISH_INTERVAL_TIME >= intervalTime){
//            super.onBackPressed()
//        }else{
//            backPressedTime = tempTime
//            Toast.makeText(applicationContext,"뒤로가기 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
//        }

    }
}