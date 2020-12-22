package com.example.goodsafe.repository

import com.example.goodsafe.model.vo.*
import retrofit2.http.*
interface ServiceApi {
    companion object {
        val BASE_URL: String = "http://ec2-15-164-129-208.ap-northeast-2.compute.amazonaws.com:3000"
        val OPEN_DATA_URL :String = "http://openapi.seoul.go.kr:8088/6b62436b676a797739386c494c5a58/json/tbEmgcAedInfo/"
    }

    @GET("/goodSafe/getEmergencyRoom")
    suspend fun getEmergencyRoom() : EmergencyRoomInfo

    @GET("/goodSafe/getNearHospital")
    suspend fun getNeaerHospital(
        @Query("curLat") curLat : Double,
        @Query("curLng") curLng : Double
    ) : NearHospitalInfo

    @GET("/goodSafe/emergencyAed")
    suspend fun getEmergencyAed(
        @Query("curLat") curLat : Double,
        @Query("curLng") curLng : Double
    ) : EmergencyAedInfo
}