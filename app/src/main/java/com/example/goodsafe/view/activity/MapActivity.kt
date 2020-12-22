package com.example.goodsafe.view.activity

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.lifecycle.Observer
import com.example.goodsafe.R
import com.example.goodsafe.view.fragment.MapFragment
import com.example.goodsafe.viewModel.MapViewModel
import com.google.android.material.snackbar.Snackbar
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.fragment_map.map_view
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapActivity : AppCompatActivity(),MapView.POIItemEventListener {

    private lateinit var curLatitude: String
    private lateinit var curLongitude: String
    private var kakaoUrl: String =""

    val viewModel by viewModels<MapViewModel>()

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            Log.d(HomeActivity.TAG, "$isGranted: ")
            if (isGranted) {
                Log.d(HomeActivity.TAG, "permission : permission is granted")
//                viewModel.requestPermission()
            } else {
                Log.d(HomeActivity.TAG, "no")
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        //권한 요청이 되어있지 않다면
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //권한요청
            requestPermission.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("위치 권한 서비스")
            builder.setMessage("위치 권한을 허용하시겠습니까?")
            builder.setIcon(R.drawable.baseline_location_on_24)

            val listener = object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, p: Int) {
                    when (p) {
                        DialogInterface.BUTTON_POSITIVE ->
                            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                        DialogInterface.BUTTON_NEGATIVE->
                            Toast.makeText(this@MapActivity, "위치 서비스를 허용해주세요", Toast.LENGTH_LONG)
                                .show()
                    }
                }
            }
            builder.setPositiveButton("확인", listener)
            builder.setNegativeButton("취소", listener)
            builder.show()

        }
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED){
            viewModel.getXY()
            //응급실 데이터 요청

        }

        slidingView.isTouchEnabled=false
        slidingView.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener{
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                Log.d("PAMEL", "onPanelSlide: ${slidingView.panelState}")

            }
            var panelState : Boolean = false
            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {

                Log.d("state", "onPanelStateChanged: ${panelState}")
                if(slidingView.panelState== SlidingUpPanelLayout.PanelState.COLLAPSED){
                    Log.d("state", "onPanelSlide: COLLAPSED")
                    slidingView.isTouchEnabled=false
                    panelState = false
                    dragView.setBackgroundColor(Color.TRANSPARENT)

                }else if(slidingView.panelState== SlidingUpPanelLayout.PanelState.EXPANDED){
                    Log.d("state", "onPanelSlide: EXPANDED")
                    slidingView.isTouchEnabled=true
                    panelState = true
                }else if(slidingView.panelState ==SlidingUpPanelLayout.PanelState.DRAGGING){
                    if(panelState){
                        slidingView.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    }else{
                        dragView.setBackgroundResource(R.drawable.form_drag_view)

                    }

                }
            }
        })
        val mapView = MapView(this)
        viewModel.point.observe(this, Observer { location ->

            curLatitude = location.latitude.toString()
            curLongitude = location.longitude.toString()
            Log.d(MapFragment.TAG, "onViewCreated: $curLongitude, $curLatitude")
            mapView.setMapCenterPoint(
                MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude),
                true
            )
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
        viewModel.emergencyRoomLd.observe(this, Observer {
            mapView.removeAllPOIItems()

            // 읽어온 응급실 좌표로 마커를 생성
            for (i in it.indices) {
                val marker = MapPOIItem()
                marker.itemName = it[i].hName
                marker.tag = i
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(
                    it[i].lat,
                    it[i].lng
                )
                marker.isShowCalloutBalloonOnTouch = true
                marker.markerType = MapPOIItem.MarkerType.YellowPin; // 기본으로 제공하는 BluePin 마커 모양.
                marker.selectedMarkerType =
                    MapPOIItem.MarkerType.RedPin; // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                mapView.addPOIItem(marker);
            }
        })


        viewModel.emgcAedLiveData.observe(this, Observer {
            mapView.removeAllPOIItems()
            for (i in it.indices) {
                val marker = MapPOIItem()
                marker.itemName = it[i].org
                marker.tag = i
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(
                    it[i].latitude,
                    it[i].longitude
                )
                marker.markerType = MapPOIItem.MarkerType.YellowPin; // 기본으로 제공하는 BluePin 마커 모양.
                marker.selectedMarkerType =
                    MapPOIItem.MarkerType.RedPin; // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                mapView.addPOIItem(marker);
            }
        })
        mapView.setPOIItemEventListener(this)
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            Log.d("Asd", "onCreate: itemClick")

            when (item.itemId) {

                R.id.find_aed->{
                    if(viewModel.point.value!=null){
                        viewModel.getEmgcAed(curLatitude.toDouble(),curLongitude.toDouble())
                    }
                }

                R.id.find_emergency_room->{
                    viewModel.getEmergencyRoom()
                }
                R.id.find_way -> {
                    if(kakaoUrl!=""){
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(kakaoUrl)))
                    }else{
                        Snackbar.make(slidingView,"목적지를 선택해주세요",Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
            true
        }
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        val lat = p1?.mapPoint?.mapPointGeoCoord?.latitude.toString()
        val lng = p1?.mapPoint?.mapPointGeoCoord?.longitude.toString()
        kakaoUrl = "kakaomap://route?sp=$curLatitude,$curLongitude&ep=$lat,$lng&by=CAR"
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
        tel_text.text = p1?.itemName
       val obj = viewModel.getMarkerDetail(p1?.itemName)
        tel_text.text = obj?.buildAddress +": "+ obj?.buildPlace +":" + obj?.managerTel
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {
        slidingView.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
        TODO("Not yet implemented")
    }

    override fun onPause() {
        super.onPause()
        viewModel.removeLocationUpdates()
    }

}