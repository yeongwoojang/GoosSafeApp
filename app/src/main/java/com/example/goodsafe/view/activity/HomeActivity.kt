package com.example.goodsafe.view.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.example.goodsafe.R
import com.example.goodsafe.viewModel.HomeViewModel
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    val viewModel by viewModels<HomeViewModel>()
    private var locationCallback = LocationCallback()
    private var locationRequest = LocationRequest()

    companion object {
        val TAG = HomeActivity::class.java.simpleName
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            Log.d(TAG, "$isGranted: ")
            if (isGranted) {
                Log.d(TAG, "permission : permission is granted")
//                viewModel.requestPermission()
            } else {
                Log.d(TAG, "no")
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


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
                            Toast.makeText(this@HomeActivity, "위치 서비스를 허용해주세요", Toast.LENGTH_LONG)
                                .show()

                    }
                }
            }
            builder.setPositiveButton("확인", listener)
            builder.setNegativeButton("취소", listener)
            builder.show()

        }

        explain_bt.setOnClickListener {
            startActivity(Intent(this, ExplainActivity::class.java))
        }

        hpt_list_bt.setOnClickListener {
            startActivity(Intent(this, HptListActivity::class.java))
        }

        map_bt.setOnClickListener {
            startActivity(Intent(this,MapActivity::class.java))
        }

    }

    override fun onPause() {
        super.onPause()
        viewModel.removeLocationUpdates()
    }
}
