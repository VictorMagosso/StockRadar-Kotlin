package com.victor.stockradar

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.victor.stockradar.repository.StockRepository
import com.victor.stockradar.viewmodel.StockViewModel
import com.victor.stockradar.viewmodel.factory.StockViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: StockViewModel
    val stockCode = "bidi4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val stock = findViewById<TextView>(R.id.txtStock)

        val viewModelFactory = StockViewModelFactory(StockRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(StockViewModel::class.java)

        viewModel.getFund(stockCode)
        viewModel.stockResponse.observe(this, { res ->
            stock.text = res.body()?.results!![stockCode.uppercase()]?.stockName
        })
    }
}