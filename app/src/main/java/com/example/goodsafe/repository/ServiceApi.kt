package com.example.goodsafe.repository

import com.example.goodsafe.model.vo.EmergencyRoom
import com.example.goodsafe.model.vo.EmergencyRoomInfo
import com.example.goodsafe.model.vo.NearHospitalInfo
import retrofit2.http.*
interface ServiceApi {
    companion object {
        val BASE_URL: String = "http://ec2-15-164-129-208.ap-northeast-2.compute.amazonaws.com:3000"
    }

    @GET("/goodSafe/getEmergencyRoom")
    suspend fun getEmergencyRoom() : EmergencyRoomInfo

    @GET("/goodSafe/getNearHospital")
    suspend fun getNeaerHospital(
        @Query("curLat") curLat : Double,
        @Query("curLng") curLng : Double
    ) : NearHospitalInfo
}