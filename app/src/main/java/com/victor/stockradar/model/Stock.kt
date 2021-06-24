package com.victor.stockradar.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Stock(
    @Expose
    val name: String,
    @Expose
    val company_name: String,
    @Expose
    val description: String,
    @Expose
    val price: String,
    @Expose
    val symbol: String,
    @Expose
    val change_percent: String
    )
