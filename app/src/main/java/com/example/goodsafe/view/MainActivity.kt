package com.example.goodsafe.view

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.goodsafe.R
import com.example.goodsafe.viewModel.TestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object{
        val TAG = MainActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel by viewModels<TestViewModel>()

        viewModel.getEmergencyRoom()
        val mapView = MapView(this)
        // 중심점 변경
        mapView.setMapCenterPoint(
            MapPoint.mapPointWithGeoCoord(37.517817799999996, 126.9355584),
            true
        );
        // 줌 인
        mapView.zoomIn(true);
        // 줌 아웃
        mapView.zoomOut(true);
        mapView.setShowCurrentLocationMarker(true)
        mapView.setCurrentLocationRadius(3000)
//        MapView.setMapTilePersistentCacheEnabled(true)
        map_view.addView(mapView)

        val geocoder  = Geocoder(this)
        viewModel.emergencyRoomLd.observe(this, Observer {
            Thread{
            val address = geocoder.getFromLocationName(it.get(0).address,1)
            val marker = MapPOIItem()
            marker.itemName = "Test Marker"
            marker.tag = 0
            marker.mapPoint = MapPoint.mapPointWithGeoCoord(address.get(0).latitude.toDouble(),address.get(0).longitude.toDouble())
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            mapView.addPOIItem(marker);

            }.start()
        })

    }

}