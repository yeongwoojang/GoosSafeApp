package com.example.goodsafe.model.vo

import com.google.gson.annotations.SerializedName

data class EmergencyRoomInfo(
    @SerializedName("code") var code : Int,
    @SerializedName("jsonArray") var emergencyRoom: List<EmergencyRoom>
)