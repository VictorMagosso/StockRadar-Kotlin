package com.victor.stockradar.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(
    context: Context?,
    name: String? = "Stock",
    factory: SQLiteDatabase.CursorFactory?,
    version: Int = 1,
    errorHandler: DatabaseErrorHandler?
) : SQLiteOpenHelper(context, name, factory, version, errorHandler) {

    private lateinit var db: SQLiteDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        this.db = db!!

        val SQL_CREATE_STOCKS = "CREATE TABLE " +
                DatabaseContractor.StockTable.TABLE_NAME + " ( " +
                DatabaseContractor.StockTable.COLUMN_SYMBOL + " TEXT PRIMARY)"

        db.execSQL( SQL_CREATE_STOCKS );
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL( "DROP TABLE IF EXISTS " + DatabaseContractor.StockTable.TABLE_NAME );
        onCreate( db );
    }

    fun addStock(symbol: String) {
        val cv: ContentValues = ContentValues()
        cv.put(DatabaseContractor.StockTable.COLUMN_SYMBOL, symbol)
        db.insert(DatabaseContractor.StockTable.TABLE_NAME, null, cv)
    }

    fun getAllStocks(): MutableList<String> {
        db = readableDatabase
        val c: Cursor = db.rawQuery("SELECT * FROM ${DatabaseContractor.StockTable.TABLE_NAME}", null)

        val list = mutableListOf<String>()

        if (c.moveToFirst()) {
            do {
                list.add(c.getString(c.getColumnIndex(DatabaseContractor.StockTable.COLUMN_SYMBOL)))
            } while (c.moveToFirst())
        }
        c.close()
        return list
    }
}