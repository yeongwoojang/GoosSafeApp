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
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodsafe.di.qualifier.OpenAPIClient
import com.example.goodsafe.model.vo.EmergencyRoom
import com.example.goodsafe.model.vo.EmgcAed
import com.example.goodsafe.repository.ServiceApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapViewModel @ViewModelInject constructor(
    private val service: ServiceApi,
    @OpenAPIClient private val customService :ServiceApi,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {

    companion object {
        val TAG = MapViewModel::class.java.simpleName
    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.let {
                for((i, location) in it.locations.withIndex()) {
                    Log.d(HomeViewModel.TAG, "$i ${location.latitude} , ${location.longitude}")
                }
            }
        }
    }

    val point = MutableLiveData<Location>()
    val emergencyRoomLd = MutableLiveData<List<EmergencyRoom>>()
    val emgcAedLiveData = MutableLiveData<List<EmgcAed>>()

    fun getEmergencyRoom() {
        viewModelScope.launch {
            val test = service.getEmergencyRoom().emergencyRoom
            Log.d(TAG, "getEmergencyRoom: ${test.toString()}")
            emergencyRoomLd.value = test
        }
    }


    @SuppressLint("MissingPermission")
    fun getXY() {

        val locationRequest = LocationRequest.create()

        locationRequest.run{
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 60*1000
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            point.value = location
            Log.d(TAG, "위치정보를 불러왔습니다.")
        }.addOnFailureListener { exception ->
            //위치 정보 불러오기 실패 시
            Log.d(TAG, "위치 정보를 불러오지 못했습니다.")
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
    }

    fun removeLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    fun getEmgcAed(){
        viewModelScope.launch {
            val data = customService.getAedInfo()
            Log.d(TAG, "getEmgcAed: ${data.toString()}")
        }
    }
}