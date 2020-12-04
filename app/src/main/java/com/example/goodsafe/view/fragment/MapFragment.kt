package com.example.goodsafe.view.fragment

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.goodsafe.R
import com.example.goodsafe.viewModel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_map.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


@AndroidEntryPoint
class MapFragment : Fragment() {

    companion object{
        val TAG = MapFragment::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Start")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: Start")
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: Start")

        val viewModel by activityViewModels<MapViewModel>()
        viewModel.getEmergencyRoom()

        val lm = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled: Boolean = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled: Boolean = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        //매니패스트에 권한이 추가되어 있어도 여기서 다시 한번 확인해야 한다.
        if (Build.VERSION.SDK_INT >= 20
            && ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
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

                    viewModel.map.setMapCenterPoint(
                        MapPoint.mapPointWithGeoCoord(getLatitude, getLongitude),
                        true
                    );
                }
                isGPSEnabled -> {
                    val location =
                        lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)// GPS 기반으로 위치를 찾음
                    val getLongitude = location?.longitude!!
                    val getLatitude = location.latitude

                    viewModel.map.setMapCenterPoint(
                        MapPoint.mapPointWithGeoCoord(getLatitude, getLongitude),
                        true
                    )
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

            //같은 영역의 지도를 다시 로드할때 캐쉬에 저장된 지도 타일들을
            // 네트워크를 거치지 않고 캐쉬에서 바로 로드하여 네트워크 사용량과 로딩 시간을 줄일 수있게한다.
//            MapView.setMapTilePersistentCacheEnabled(true)
//            // 줌레밸 변경
//            mapView.setZoomLevel(4, true);
//            // 줌 인
//            mapView.zoomIn(true);
//            // 줌 아웃
//            mapView.zoomOut(true);
//            //현재위치에 마커 표시
//            mapView.setShowCurrentLocationMarker(true)
//            mapView.setCurrentLocationRadius(1000)
//            MapView.setMapTilePersistentCacheEnabled(true)
//            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
            map_view.addView(viewModel.map)




        //DB에서 응급실의 좌표들을 다 읽었을 때 실행
        viewModel.emergencyRoomLd.observe(requireActivity(), Observer {
            // 읽어온 응급실 좌표로 마커를 생성
            for (i in it.indices) {
                val marker = MapPOIItem()
                marker.itemName = it[i].hName
                marker.tag = i
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(
                    it[i].lng.toDouble(),
                    it[i].lat.toDouble()
                )
                marker.markerType = MapPOIItem.MarkerType.BluePin; // 기본으로 제공하는 BluePin 마커 모양.
                marker.selectedMarkerType =
                    MapPOIItem.MarkerType.RedPin; // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                viewModel.map.addPOIItem(marker);
            }

        })
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