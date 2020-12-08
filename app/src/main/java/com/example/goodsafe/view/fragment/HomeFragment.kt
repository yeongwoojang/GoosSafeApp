//package com.example.goodsafe.view.fragment
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.activityViewModels
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.Observer
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.goodsafe.R
//import com.example.goodsafe.view.adapter.ListAdapter
//import com.example.goodsafe.viewModel.HomeViewModel
//import com.example.goodsafe.viewModel.MapViewModel
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.android.synthetic.main.fragment_home.*
//
//@AndroidEntryPoint
//class HomeFragment : Fragment() {
//
//    val viewModel by viewModels<HomeViewModel>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_home, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val adapter = ListAdapter(requireContext())
//
//        recyclerView.apply {
//            this.layoutManager =LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
//            this.adapter = adapter
//        }
//        viewModel.point.observe(requireActivity(), Observer {point->
//            viewModel.getNearHostpital(point.latitude, point.longitude)
//        })
//        viewModel.nearHospital.observe(requireActivity(), Observer {
//            adapter.updateItems(it)
//        })
//    }
//}