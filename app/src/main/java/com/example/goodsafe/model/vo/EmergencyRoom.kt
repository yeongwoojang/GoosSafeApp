package com.example.goodsafe.model.vo

import com.google.gson.annotations.SerializedName

data class EmergencyRoom(
    @SerializedName("H_NO") var hNo : Int,
    @SerializedName("H_NAME") var hName : String,
    @SerializedName("H_WORK") var hWork : String,
    @SerializedName("TELL") var tell : String,
    @SerializedName("ADDRESS") var address : String,
    @SerializedName("MON") var mon : String,
    @SerializedName("TUES") var tues : String,
    @SerializedName("WEND") var wend : String,
    @SerializedName("THUR") var thur : String,
    @SerializedName("FRI") var fri : String,
    @SerializedName("SAT") var sat : String,
    @SerializedName("SUN") var sun : String,
    @SerializedName("HOL") var hol : String,
    @SerializedName("LATITUDE") var lat : Double,
    @SerializedName("LONGITUDE") var lng : Double
)