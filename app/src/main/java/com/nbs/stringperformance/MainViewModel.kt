package com.nbs.stringperformance

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.nbs.stringperformance.data.DbContract
import com.nbs.stringperformance.data.DbHelper
import com.nbs.stringperformance.data.stringResources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.InputStream


class MainViewModel : ViewModel() {
    private val _wordController = MutableStateFlow("String Resources")
    val wordController = _wordController.asStateFlow()

    private val _timerController = MutableStateFlow("0 ms")
    val timerController = _timerController.asStateFlow()

    private var startTime: Long = 0L

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

        _timerController.value = "${System.currentTimeMillis() - startTime} ms"
    }

    fun loadFromXmlArray(context: Context) {

        resetWordController()

        val strings = context.resources.getStringArray(R.array.data)
        for (string in strings) {
            val prefix = if (_wordController.value.isEmpty()) {
                ""
            } else "${_wordController.value}, "
            _wordController.value = prefix + string
        }

        Log.d("TAG", "loadFromXml: ${_wordController.value}")

        _timerController.value = "${System.currentTimeMillis() - startTime} ms"
    }

    fun loadFromXml(context: Context) {
        resetWordController()

        for (string in stringResources) {
            val prefix = if (_wordController.value.isEmpty()) {
                ""
            } else "${_wordController.value}, "
            _wordController.value = prefix + context.resources.getString(string)
        }

        Log.d("TAG", "loadFromXml: ${_wordController.value}")

        _timerController.value = "${System.currentTimeMillis() - startTime} ms"
    }

    fun loadFromJson(context: Context) {
        resetWordController()

        val inputStream: InputStream = context.assets.open("strings.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val json = Gson().fromJson(jsonString, Map::class.java)

        for (i in 1..150) {
            val prefix = if (_wordController.value.isEmpty()) {
                ""
            } else "${_wordController.value}, "
            _wordController.value = prefix + json["item$i"]
        }
        Log.d("TAG", "loadFromJson: ${_wordController.value}")

        _timerController.value = "${System.currentTimeMillis() - startTime} ms"
    }

    private fun resetWordController() {
        _wordController.value = ""
        Log.d("TAG", "resetWordController: ${_wordController.value}")

        startTime = System.currentTimeMillis()
    }
}