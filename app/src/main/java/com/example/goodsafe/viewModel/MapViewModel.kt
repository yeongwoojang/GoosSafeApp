package com.example.goodsafe.viewModel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodsafe.model.vo.EmergencyRoom
import com.example.goodsafe.repository.ServiceApi
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapViewModel @ViewModelInject constructor(
    private val service: ServiceApi,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {

    companion object {
        val TAG = MapViewModel::class.java.simpleName
    }



    val point = MutableLiveData<Location>()
    val emergencyRoomLd = MutableLiveData<List<EmergencyRoom>>()


    fun getEmergencyRoom() {
        viewModelScope.launch {
            val test = service.getEmergencyRoom().emergencyRoom
            emergencyRoomLd.value = test
        }
    }


    @SuppressLint("MissingPermission")
    fun getXY() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            point.value = location
            Log.d(TAG, "위치정보를 불러왔습니다.")
        }.addOnFailureListener { exception ->
            //위치 정보 불러오기 실패 시
            Log.d(TAG, "위치 정보를 불러오지 못했습니다.")
        }
    }
}