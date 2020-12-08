package com.example.goodsafe.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.goodsafe.R
import com.example.goodsafe.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    val viewModel by viewModels<HomeViewModel>()

    companion object {
        val REQUEST_CODE = 1
        val TAG = HomeActivity::class.java.simpleName
    }
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted : Boolean ->

            if(isGranted){
                Log.d(TAG, "ok")
                viewModel.requestPermission()
            }else{
                Log.d(TAG, "no")
            }

        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //권한 요청이 되어있지 않다면
        when{
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED->{
                requestPermission.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }else->{
            viewModel.requestPermission()
        }
        }




        explain_bt.setOnClickListener {
            startActivity(Intent(this,ExplainActivity::class.java))
        }
        hpt_list_bt.setOnClickListener {
            startActivity(Intent(this,HptListActivity::class.java))
        }
    }

    //    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        Log.d("test", "onRequestPermissionsResult: ok")
//        if (requestCode == REQUEST_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                viewModel.getXY()
//            } else {
//                Toast.makeText(this, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//    }

}
