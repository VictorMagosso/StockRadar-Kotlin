package com.victor.stockradar.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class Database(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val SQL_CREATE_STOCKS = "CREATE TABLE " +
                DatabaseContractor.StockTable.TABLE_NAME + " ( " +
                DatabaseContractor.StockTable.COLUMN_SYMBOL + " TEXT )"

        db?.execSQL( SQL_CREATE_STOCKS );
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL( "DROP TABLE IF EXISTS " + DatabaseContractor.StockTable.TABLE_NAME );
        onCreate( db );
    }

    fun addStock(symbol: String) {
        val db = writableDatabase
        val cv: ContentValues = ContentValues()
        cv.put(DatabaseContractor.StockTable.COLUMN_SYMBOL, symbol)
        db.insert(DatabaseContractor.StockTable.TABLE_NAME, null, cv)
    }

    fun getAllStocks(): MutableList<String> {
        val db = readableDatabase
        val c: Cursor
        try {
            c = db.rawQuery("SELECT * FROM ${DatabaseContractor.StockTable.TABLE_NAME}", null)
        } catch (e: Exception) {
            throw e
        }

        val list = mutableListOf<String>()
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    list.add(c.getString(c.getColumnIndex(DatabaseContractor.StockTable.COLUMN_SYMBOL)))
                } while (c.moveToNext())
            }
        }
        c.close()
        return list
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "Stocks"
    }
}