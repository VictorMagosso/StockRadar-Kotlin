package com.victor.stockradar.api

import com.victor.stockradar.constants.Constants
import com.victor.stockradar.service.StockService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun stockService(): StockService = retrofit.create(StockService::class.java)
}