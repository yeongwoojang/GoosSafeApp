package com.example.goodsafe.model.vo

import com.google.gson.annotations.SerializedName

data class EmgcAedInfo(
    @SerializedName("list_total_count") var listTotalCount : Int,
    @SerializedName("RESULT") var result : Result,
    @SerializedName("row")  var row : List<EmgcAed>
)