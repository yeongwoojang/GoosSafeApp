package com.example.goodsafe.model.vo

import com.google.gson.annotations.SerializedName

data class EmergencyAed(
    @SerializedName("MANAGER") var manager : String,
    @SerializedName("MANAGERTEL") var managerTel : String,
    @SerializedName("BUILDADDRESS") var buildAddress : String,
    @SerializedName("BUILDPLACE") var buildPlace : String,
    @SerializedName("ORG") var org : String,
    @SerializedName("CLERKTEL") var clerkTel : String,
    @SerializedName("LONGITUDE") var longitude : Double,
    @SerializedName("LATITUDE") var latitude : Double,
    @SerializedName("distance") var distance : Double
)