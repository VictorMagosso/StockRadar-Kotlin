package com.victor.stockradar.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponse (
    @SerializedName("results")
    @Expose
    val results: HashMap<String,Stock>,
)