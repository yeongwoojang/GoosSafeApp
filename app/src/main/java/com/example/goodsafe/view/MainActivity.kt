package com.example.goodsafe.view

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.goodsafe.R
import com.example.goodsafe.viewModel.TestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.jar.Manifest


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val viewModel by viewModels<TestViewModel>()
        val mapView = MapView(this)
        viewModel.getEmergencyRoom()
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled: Boolean = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled: Boolean = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        //매니패스트에 권한이 추가되어 있어도 여기서 다시 한번 확인해야 한다.
        if (Build.VERSION.SDK_INT >= 20
            && ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        } else {
            when { //프로바이더 제공자 활성화 여부 체크
                isNetworkEnabled -> {
                    val location =
                        lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) //인터넷 기반으로 위치를 찾음
                   val getLongitude = location?.longitude!!
                   val getLatitude = location.latitude

                    mapView.setMapCenterPoint(
                        MapPoint.mapPointWithGeoCoord(getLatitude, getLongitude),
                        true
                    );
                }
                isGPSEnabled -> {
                    val location =
                        lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)// GPS 기반으로 위치를 찾음
                    val getLongitude = location?.longitude!!
                    val getLatitude = location.latitude

                    mapView.setMapCenterPoint(
                        MapPoint.mapPointWithGeoCoord(getLatitude, getLongitude),
                        true
                    );
                }
                else -> {

                }
            }
            //몇 초 간격과 몇미터를 이동했을 시에 호출되는 부분 - 주기적으로 위치 업데이트를 하고 싶다면 사용
            //****주기적 업데이트를 사용하다가 사용안할시에는 반드시 해제 필요
//            lm.requestLocationUpdates(
//                LocationManager.NETWORK_PROVIDER,
//                1000,
//                1F,
//                gpsLocationListener
//            )
//            //해제부분, 상황에 맞게 잘 구현해야함
//            lm.removeUpdates(gpsLocationListener)
        }


        MapView.setMapTilePersistentCacheEnabled(true)
        // 중심점 변경

        // 줌 인
        mapView.zoomIn(true);
        // 줌 아웃
        mapView.zoomOut(true);
        //현재위치에 마커 표시
        mapView.setShowCurrentLocationMarker(true)
        mapView.setCurrentLocationRadius(3000)
        MapView.setMapTilePersistentCacheEnabled(true)
        map_view.addView(mapView)

        val geocoder = Geocoder(this)
        viewModel.emergencyRoomLd.observe(this, Observer {
            Thread {
                val address = geocoder.getFromLocationName(it.get(0).address, 1)
                val marker = MapPOIItem()
                marker.itemName = "Test Marker"
                marker.tag = 0
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(
                    address.get(0).latitude.toDouble(),
                    address.get(0).longitude.toDouble()
                )
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                mapView.addPOIItem(marker);

            }.start()
        })

    }

    fun gpsEnableed(lm: LocationManager) {

    }

    val gpsLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val provider: String = location.provider
            val longitude: Double = location.longitude
            val latitude: Double = location.latitude
            val altitude: Double = location.altitude
        }

        //아래 3개 함수는 형식상 필수부분
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            super.onStatusChanged(provider, status, extras)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }
    }

}