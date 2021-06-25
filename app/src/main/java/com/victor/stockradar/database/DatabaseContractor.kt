package com.victor.stockradar.database

import android.provider.BaseColumns

class DatabaseContractor private constructor() {
    object StockTable : BaseColumns {
        const val TABLE_NAME = "stock_table"
        const val COLUMN_SYMBOL = "symbol"
    }
}