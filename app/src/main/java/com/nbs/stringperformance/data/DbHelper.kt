package com.nbs.stringperformance.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "Strings.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DbContract.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DbContract.SQL_DELETE_TABLE)
        onCreate(db)
    }

    fun resetTable(db: SQLiteDatabase?) {
        db?.apply {
            execSQL(DbContract.SQL_DELETE_TABLE)
            execSQL(DbContract.SQL_CREATE_TABLE)
        }
    }
}