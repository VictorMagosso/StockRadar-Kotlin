package com.victor.stockradar.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Stock(
    @SerializedName("name")
    @Expose
    val stockName: String
)
