package com.victor.stockradar.repository

import android.content.Context
import com.victor.stockradar.api.RetrofitConfig
import com.victor.stockradar.database.Database
import com.victor.stockradar.model.ApiResponse
import com.victor.stockradar.model.Stock
import com.victor.stockradar.service.StockService
import retrofit2.Response
import java.lang.Exception

class StockRepository {
    suspend fun getStock(stockCode: String): Response<ApiResponse> {
        return RetrofitConfig().stockService().getStock(stockCode)
    }
    fun insertStock(context: Context, stockCode: String) {
        val db = Database(context)
        try {
            db.addStock(stockCode)
        } catch (e: Exception) {
            throw e
        }
    }
    fun getStocks(context: Context): MutableList<String> {
        val db = Database(context)
        val list = mutableListOf<String>()
        try {
            return db.getAllStocks()
        } catch (e: Exception) {
            throw e
        }
    }
}