//package com.example.goodsafe.view.activity
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.util.Log
//import android.view.MenuItem
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.view.forEach
//import androidx.navigation.Navigation
//import androidx.navigation.findNavController
//import androidx.navigation.ui.NavigationUI
//import com.example.goodsafe.R
//import com.example.goodsafe.viewModel.MapViewModel
//import com.google.android.material.bottomnavigation.BottomNavigationView
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.android.synthetic.main.activity_bottom_nav.*
//
//@AndroidEntryPoint
//class BottomNavActivity : AppCompatActivity() {
//
//    val viewModel by viewModels<MapViewModel>()
//    var menuItem: Int = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_bottom_nav)
//
//
//        NavigationUI.setupWithNavController(
//            bottom_navigation_view,
//            findNavController(R.id.nav_host)
//        )
//        bottom_navigation_view.setOnNavigationItemReselectedListener { item ->
//            when (item.itemId) {
//                R.id.homeFragment -> {
//                    menuItem = item.itemId
//                }
//                R.id.mapFragment -> {
//                    menuItem = item.itemId
//                }
//                R.id.listFragment -> {
//                    menuItem = item.itemId
//                }
//            }
//        }
//        bottom_navigation_view.menu.forEach {
//            if (it.itemId == menuItem) {
//                it.isEnabled = false
//            }
//        }
//    }
//
//}