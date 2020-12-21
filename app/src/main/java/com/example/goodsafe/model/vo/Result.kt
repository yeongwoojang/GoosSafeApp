package com.example.goodsafe.model.vo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("CODE")
    @Expose
    var code: String,
    @SerializedName("MESSAGE")
    @Expose
    var message: String
)