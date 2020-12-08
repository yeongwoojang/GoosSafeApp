package com.example.goodsafe.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    val viewModel by viewModels<HomeViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hpt_list)

        val adapter = ListAdapter(this)
        viewModel.getXY()
        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@HptListActivity, RecyclerView.VERTICAL,false)
            this.adapter = adapter
        }
        viewModel.point.observe(this, Observer {point->
            viewModel.getNearHostpital(point.latitude, point.longitude)
        })
        viewModel.nearHospital.observe(this, Observer {
            adapter.updateItems(it)
        })
    }
}