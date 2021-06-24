package com.victor.stockradar

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.victor.stockradar.repository.StockRepository
import com.victor.stockradar.viewmodel.StockViewModel
import com.victor.stockradar.viewmodel.factory.StockViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: StockViewModel
    private var stockCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val stock = findViewById<TextView>(R.id.txtStock)
        val stockPrice = findViewById<TextView>(R.id.txtPrice)
        val stockDescription = findViewById<TextView>(R.id.txtDescription)
        val stockCompanyName = findViewById<TextView>(R.id.txtCompanyName)
        val editStock = findViewById<EditText>(R.id.editStockCode)
        val btnChooseStock = findViewById<Button>(R.id.btnChooseStock)

        val viewModelFactory = StockViewModelFactory(StockRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(StockViewModel::class.java)

        btnChooseStock.setOnClickListener {
            stockCode = editStock.text.toString().trim().lowercase()
            viewModel.getFund(stockCode)
            viewModel.stockResponse.observe(this, { res ->
                stock.text = res.body()?.results!![stockCode.uppercase()]?.symbol
                stockPrice.text = "R\$ ${res.body()?.results!![stockCode.uppercase()]?.price?.replace(".", ",")}"
                stockCompanyName.text = res.body()?.results!![stockCode.uppercase()]?.company_name
                stockDescription.text = res.body()?.results!![stockCode.uppercase()]?.description
            })
        }
    }
}