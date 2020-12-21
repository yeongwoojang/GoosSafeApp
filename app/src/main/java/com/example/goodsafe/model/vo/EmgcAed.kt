package com.example.goodsafe.model.vo

import com.google.gson.annotations.SerializedName

data class EmgcAed(
    @SerializedName("MODEL") var model : String,
    @SerializedName("ZIPCODE1") var zipCode1 : String,
    @SerializedName("ZIPCODE2") var zipCode2 : String,
    @SerializedName("MANAGER") var manager : String,
    @SerializedName("BUILDADDRESS") var buildAddress : String,
    @SerializedName("CLERKTEL") var cleckTel : String,
    @SerializedName("BUILDPLACE")  var buildPlace : String,
    @SerializedName("MFG") var mfg : String,
    @SerializedName("WGS84LON") var longitude : Double,
    @SerializedName("WGS84LAT") var latitude : Double
)