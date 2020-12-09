package com.example.goodsafe.viewModel

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodsafe.model.vo.NearHospital
import com.example.goodsafe.repository.ServiceApi
import com.example.goodsafe.view.activity.HomeActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val service : ServiceApi,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel(){

    companion object{
        val TAG = HomeViewModel::class.java.simpleName
    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.let {
                for((i, location) in it.locations.withIndex()) {
                    Log.d(TAG, "$i ${location.latitude} , ${location.longitude}")
                }
            }
        }
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
    fun updateLocation() {
        val locationRequest = LocationRequest.create()

        locationRequest.run{
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 60*1000
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            point.value = location
            Log.d(TAG, "getXY: ${location?.latitude}")
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
}