package com.nbs.stringperformance

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nbs.stringperformance.data.DbContract
import com.nbs.stringperformance.data.DbHelper


class MainViewModel : ViewModel() {
    var wordController = mutableStateOf("String resources")

    fun initDb(context: Context, dbHelper: DbHelper) {
        val enStrings = context.resources.getStringArray(R.array.data)

        val db = dbHelper.writableDatabase

        dbHelper.resetTable(db)

        for (i in enStrings.indices) {
            db?.insert(
                DbContract.StringEntry.TABLE_NAME,
                null,
                ContentValues().apply {
                    put(DbContract.StringEntry.COLUMN_TEXT, enStrings[i])
                }
            )
        }
    }

    fun loadFromDb(dbHelper: DbHelper) {
        wordController.value = ""

        val db = dbHelper.readableDatabase

        for (i in 0..149) {
            val cursor = db.query(
                DbContract.StringEntry.TABLE_NAME,
                arrayOf(
                    BaseColumns._ID,
                    DbContract.StringEntry.COLUMN_TEXT,
                ),
                "${BaseColumns._ID} = ?",
                arrayOf("$i"),
                null,
                null,
                null,
            )
            with (cursor) {
                while (moveToNext()) {
                    val prefix = if (wordController.value.isEmpty()) {
                        ""
                    } else "${wordController.value}, "
                    wordController.value = prefix + getString(
                        getColumnIndexOrThrow(DbContract.StringEntry.COLUMN_TEXT)
                    )
                }
            }
            cursor.close()
        }
        Log.d("TAG", "loadFromDb: ${wordController.value}")
    }

    fun loadFromXml(context: Context) {
        wordController.value = ""

        val strings = context.resources.getStringArray(R.array.data)
        wordController.value = strings.joinToString(", ")
        Log.d("TAG", "loadFromXml: ${wordController.value}")
    }
}