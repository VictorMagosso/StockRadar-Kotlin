package com.victor.stockradar

import android.content.res.AssetManager
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victor.stockradar.adapter.AdapterStock
import com.victor.stockradar.model.Stock
import com.victor.stockradar.repository.StockRepository
import com.victor.stockradar.viewmodel.StockViewModel
import com.victor.stockradar.viewmodel.factory.StockViewModelFactory
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.InputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val scope = CoroutineScope(Dispatchers.IO + CoroutineName("MainScope"))
    private lateinit var viewModel: StockViewModel
    private var stockCode = ""
    private var stocksSuggestions = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val stock = findViewById<TextView>(R.id.txtStock)
        val stockPrice = findViewById<TextView>(R.id.txtPrice)
        val stockDescription = findViewById<TextView>(R.id.txtDescription)
        val stockCompanyName = findViewById<TextView>(R.id.txtCompanyName)
        val stockVariation = findViewById<TextView>(R.id.txtStockVariation)
        val editStock = findViewById<EditText>(R.id.editStockCode)
        val btnChooseStock = findViewById<Button>(R.id.btnChooseStock)
        val resultLayout = findViewById<ConstraintLayout>(R.id.resultLayout)
        val notFoundLayout = findViewById<RelativeLayout>(R.id.notFoundLayout)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val rvStocks = findViewById<RecyclerView>(R.id.rvMyStocks)

        val mockList = mutableListOf<Stock>()

        mockList.add(Stock("Neogrid", "Neogrid S.A.", "Descricao descircao da neogrid so cai meu Deus", "11.50", "NGRD3", "7%"))
        mockList.add(Stock("Neogrid", "Neogrid S.A.", "Descricao descircao da neogrid so cai meu Deus", "11.50", "NGRD3", "7%"))
        mockList.add(Stock("Neogrid", "Neogrid S.A.", "Descricao descircao da neogrid so cai meu Deus", "11.50", "NGRD3", "7%"))
        mockList.add(Stock("Neogrid", "Neogrid S.A.", "Descricao descircao da neogrid so cai meu Deus", "11.50", "NGRD3", "7%"))

        val adapterStock = AdapterStock(mockList)
        val linearLayoutManager = LinearLayoutManager(this)

        rvStocks.apply {
            adapter = adapterStock
            layoutManager = linearLayoutManager
            hasFixedSize()
        }

        val assetManager: AssetManager = assets

        try {
            val inputStream: InputStream = assetManager.open("stocks.txt")

            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val result = String(buffer)
            val resultToArray = result.trim().split(",")
            stocksSuggestions = resultToArray.sortedBy { it } as MutableList<String>
        } catch (e: Exception) {
            stocksSuggestions.add("Nenhuma sugestão")
        }

        val viewModelFactory = StockViewModelFactory(StockRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(StockViewModel::class.java)

        val items = stocksSuggestions
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (editStock as? AutoCompleteTextView)?.setAdapter(adapter)

        btnChooseStock.setOnClickListener {
            if (editStock.text.isNotEmpty()) {
                resultLayout.visibility = View.GONE
                notFoundLayout.visibility = View.GONE
                progressBar.visibility = View.VISIBLE

                stockCode = editStock.text.toString().trim().lowercase()

                scope.launch {
                    viewModel.getFund(stockCode)
                }
                viewModel.stockResponse.observe(this, { res ->
                    if (res.body()?.results!![stockCode.uppercase()]?.name != null) {
                        progressBar.visibility = View.GONE

                        stock.text = res.body()?.results!![stockCode.uppercase()]?.symbol
                        stockPrice.text = "R\$ ${res.body()?.results!![stockCode.uppercase()]?.price?.replace(".", ",")}"
                        stockCompanyName.text = res.body()?.results!![stockCode.uppercase()]?.company_name
                        stockDescription.text = res.body()?.results!![stockCode.uppercase()]?.description
                        stockVariation.text = res.body()?.results!![stockCode.uppercase()]?.change_percent
                        resultLayout.visibility = View.VISIBLE
                        notFoundLayout.visibility = View.GONE

                    } else {
                        notFoundLayout.visibility = View.VISIBLE
                        resultLayout.visibility = View.GONE
                        progressBar.visibility = View.GONE
                    }
                })
            } else {
                Toast.makeText(this, "Você precisa escolher uma ação", Toast.LENGTH_SHORT).show()
            }
        }
    }
}