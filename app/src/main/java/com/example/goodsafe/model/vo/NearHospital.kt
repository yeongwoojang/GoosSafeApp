package com.example.goodsafe.model.vo

import com.google.gson.annotations.SerializedName

data class NearHospital(
    @SerializedName("H_NAME") var hName : String,
    @SerializedName("TELL") var tell : String,
    @SerializedName("distance") var distance : Double

)