package com.nbs.stringperformance

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import com.nbs.stringperformance.data.DbContract
import com.nbs.stringperformance.data.DbHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class MainViewModel : ViewModel() {
    private val _wordController = MutableStateFlow("String Resources")
    val wordController = _wordController.asStateFlow()

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
        resetWordController()

        val db = dbHelper.readableDatabase

        for (i in 150 downTo 0) {
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
                    val prefix = if (_wordController.value.isEmpty()) {
                        ""
                    } else "${_wordController.value}, "
                    _wordController.value = prefix + getString(
                        getColumnIndexOrThrow(DbContract.StringEntry.COLUMN_TEXT)
                    )
                }
            }
            cursor.close()
        }
        Log.d("TAG", "loadFromDb: ${_wordController.value}")
    }

    fun loadFromXml(context: Context) {
        resetWordController()

        val strings = context.resources.getStringArray(R.array.data)
        _wordController.value = strings.joinToString(", ")
        Log.d("TAG", "loadFromXml: ${_wordController.value}")
    }

    fun resetWordController() {
        _wordController.value = ""
        Log.d("TAG", "resetWordController: ${_wordController.value}")
    }
}