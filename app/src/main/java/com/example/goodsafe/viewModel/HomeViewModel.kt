package com.example.goodsafe.viewModel

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodsafe.model.vo.NearHospital
import com.example.goodsafe.repository.ServiceApi
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val service : ServiceApi,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel(){

    init {
        getXY()
    }
    val point = MutableLiveData<Location>()
    val nearHospital = MutableLiveData<List<NearHospital>>()
    fun getNearHostpital(curLat : Double, curLng : Double){
        viewModelScope.launch {
            val a=  service.getNeaerHospital(curLat,curLng).nearHospital
            nearHospital.value = a
        }
    }

    @SuppressLint("MissingPermission")
    fun getXY() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            point.value = location
            Log.d(MapViewModel.TAG, "위치정보를 불러왔습니다.")
        }.addOnFailureListener { exception ->
            //위치 정보 불러오기 실패 시
            Log.d(MapViewModel.TAG, "위치 정보를 불러오지 못했습니다.")
        }
    }
}