package com.victor.stockradar.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.stockradar.model.ApiResponse
import com.victor.stockradar.model.Stock
import com.victor.stockradar.repository.StockRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class StockViewModel(private val repository: StockRepository): ViewModel() {
    val stockResponse: MutableLiveData<Response<ApiResponse>> = MutableLiveData()
    val stocks: MutableLiveData<MutableList<String>> = MutableLiveData()
    val insertMsg: MutableLiveData<String> = MutableLiveData()

    suspend fun getFund(fundName: String) {
        viewModelScope.launch{
            val response: Response<ApiResponse> = repository.getStock(fundName)
            stockResponse.value = response
        }
    }

    fun insertStock(context: Context, symbol: String) {
        try {
            repository.insertStock(context, symbol)
            insertMsg.value = "Adicionado!"
        } catch (e: Exception) {
            insertMsg.value = "Algo deu errado ao inserir: ${e.message}"
        }
    }

    fun getStocks(context: Context) {
        try {
            stocks.value = repository.getStocks(context)
        } catch (e: Exception) {
            //
        }
    }
}