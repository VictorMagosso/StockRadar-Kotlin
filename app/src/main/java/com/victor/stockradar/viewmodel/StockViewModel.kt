package com.victor.stockradar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.stockradar.model.ApiResponse
import com.victor.stockradar.model.Stock
import com.victor.stockradar.repository.StockRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class StockViewModel(private val repository: StockRepository): ViewModel() {
    val stockResponse: MutableLiveData<Response<ApiResponse>> = MutableLiveData()

    fun getFund(fundName: String) {
        viewModelScope.launch{
            val response: Response<ApiResponse> = repository.getStock(fundName)
            stockResponse.value = response
        }
    }
}