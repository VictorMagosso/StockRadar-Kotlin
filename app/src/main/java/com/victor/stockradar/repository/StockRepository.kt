package com.victor.stockradar.repository

import com.victor.stockradar.api.RetrofitConfig
import com.victor.stockradar.model.ApiResponse
import com.victor.stockradar.model.Stock
import com.victor.stockradar.service.StockService
import retrofit2.Response

class StockRepository {
    suspend fun getStock(stockCode: String): Response<ApiResponse> {
        return RetrofitConfig().stockService().getStock(stockCode)
    }
}