package com.example.goodsafe.viewModel

import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodsafe.model.vo.EmergencyRoom
import com.example.goodsafe.repository.ServiceApi
import kotlinx.coroutines.launch
import java.io.IOException

class TestViewModel @ViewModelInject constructor(
    application: Application,
    private val service : ServiceApi
) : AndroidViewModel(application){

    companion object{
        val TAG = TestViewModel::class.java.simpleName
    }
    private val context = getApplication<Application>().applicationContext
    val emergencyRoomLd = MutableLiveData<List<EmergencyRoom>>()
     fun getEmergencyRoom(){
        viewModelScope.launch { 
            val test= service.getEmergencyRoom().emergencyRoom
            emergencyRoomLd.value = test
        }
    }
}