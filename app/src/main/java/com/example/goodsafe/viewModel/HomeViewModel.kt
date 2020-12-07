package com.example.goodsafe.viewModel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodsafe.repository.ServiceApi
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val service : ServiceApi
) : ViewModel(){

    fun getNearHostpital(curLat : Double, curLng : Double){
        viewModelScope.launch {
        val test =service.getNeaerHospital(curLat,curLng).nearHospital
            Log.d("test", "getNearHostpital: ${test.toString()}")

        }
    }
}