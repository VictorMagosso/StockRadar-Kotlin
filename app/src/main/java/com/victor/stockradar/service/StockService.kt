package com.victor.stockradar.service

import com.victor.stockradar.constants.Constants
import com.victor.stockradar.model.ApiResponse
import com.victor.stockradar.model.Stock
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockService {
    @GET("stock_price?key=${Constants.API_KEY}&symbol=symbol")
    suspend fun getStock(@Query("symbol") stockCode: String) : Response<ApiResponse>
}