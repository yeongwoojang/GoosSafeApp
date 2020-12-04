package com.example.goodsafe.viewModel

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.goodsafe.model.vo.EmergencyRoom
import com.example.goodsafe.repository.ServiceApi
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapViewModel @ViewModelInject constructor(
    application: Application,
    private val service: ServiceApi
) : AndroidViewModel(application) {

    companion object {
        val TAG = MapViewModel::class.java.simpleName
    }
    lateinit var map: MapView


    private val context = getApplication<Application>().applicationContext
    val emergencyRoomLd = MutableLiveData<List<EmergencyRoom>>()
    fun getEmergencyRoom() {
        viewModelScope.launch {
            val test = service.getEmergencyRoom().emergencyRoom
            emergencyRoomLd.value = test
        }
    }

    fun createMapView(context: Context) {
        map = MapView(context)
        //같은 영역의 지도를 다시 로드할때 캐쉬에 저장된 지도 타일들을
        // 네트워크를 거치지 않고 캐쉬에서 바로 로드하여 네트워크 사용량과 로딩 시간을 줄일 수있게한다.
        MapView.setMapTilePersistentCacheEnabled(true)
        // 줌레밸 변경
        map.setZoomLevel(4, true);
        // 줌 인
        map.zoomIn(true);
        // 줌 아웃
        map.zoomOut(true);
        //현재위치에 마커 표시
        map.setShowCurrentLocationMarker(true)
        map.setCurrentLocationRadius(3000)
        map.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }
}