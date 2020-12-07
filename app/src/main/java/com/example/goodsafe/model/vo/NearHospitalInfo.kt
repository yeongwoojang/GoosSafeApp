package com.example.goodsafe.model.vo

import com.google.gson.annotations.SerializedName

data class NearHospitalInfo(
    @SerializedName("code") var code : Int,
    @SerializedName("jsonArray") var nearHospital : List<NearHospital>
)