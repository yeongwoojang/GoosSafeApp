package com.example.goodsafe.view.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_map.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


@AndroidEntryPoint
class MapFragment : Fragment(), MapView.POIItemEventListener {

    companion object {
        val TAG = MapFragment::class.java.simpleName
        val REQUEST_CODE = 1
    }

    private lateinit var curLatitude: String
    private lateinit var curLongitude: String
    private lateinit var kakaoUrl: String

    val viewModel by viewModels<MapViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //위치권한이 허용되어 있지 않다면
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //위치권한 요청
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
        } else {
            Log.d("gogo", "onCreate: permissionOk")
            viewModel.getXY()
        }
        val mapView = MapView(requireActivity())
        //응급실 데이터 요청
        viewModel.getEmergencyRoom()

        viewModel.point.observe(requireActivity(), Observer { location ->
            
            curLatitude = location.latitude.toString()
            curLongitude = location.longitude.toString()
            Log.d(TAG, "onViewCreated: $curLongitude, $curLatitude")
            mapView.setMapCenterPoint(
                MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude),
                true
            )
            MapView.setMapTilePersistentCacheEnabled(true)
            // 줌레밸 변경
            mapView.setZoomLevel(4, true);
            // 줌 인
            mapView.zoomIn(true);
            // 줌 아웃
            mapView.zoomOut(true);
            //현재위치에 마커 표시
            mapView.setShowCurrentLocationMarker(true)
            MapView.setMapTilePersistentCacheEnabled(true)
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading)
            map_view.addView(mapView)
        })
        //DB에서 응급실의 좌표들을 다 읽었을 때 실행
        viewModel.emergencyRoomLd.observe(requireActivity(), Observer {
            Log.d("sgs", "onViewCreated: ${it.toString()}")
            mapView.setCurrentLocationRadius(1500)

            // 읽어온 응급실 좌표로 마커를 생성
            for (i in it.indices) {
                val marker = MapPOIItem()
                marker.itemName = it[i].hName
                marker.tag = i
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(
                    it[i].lat,
                    it[i].lng
                )
                marker.markerType = MapPOIItem.MarkerType.BluePin; // 기본으로 제공하는 BluePin 마커 모양.
                marker.selectedMarkerType =
                    MapPOIItem.MarkerType.RedPin; // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                mapView.addPOIItem(marker);
            }
        })
        mapView.setPOIItemEventListener(this)

        search_bt.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(kakaoUrl)))

        }
    }


    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {

    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {

    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {

    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        val lat = p1?.mapPoint?.mapPointGeoCoord?.latitude.toString()
        val lng = p1?.mapPoint?.mapPointGeoCoord?.longitude.toString()
        hospital_name.text = p1?.itemName
        kakaoUrl = "kakaomap://route?sp=$curLatitude,$curLongitude&ep=$lat,$lng&by=CAR"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "onRequestPermissionsResult: ok")
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.getXY()
            } else {
                Toast.makeText(requireContext(), "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()

            }
        }

    }
}