package com.victor.stockradar.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victor.stockradar.repository.StockRepository
import com.victor.stockradar.viewmodel.StockViewModel

class StockViewModelFactory(private val repository: StockRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StockViewModel(repository) as T
    }
}