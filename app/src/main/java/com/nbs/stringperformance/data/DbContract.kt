package com.nbs.stringperformance.data

import android.provider.BaseColumns

object DbContract {
    object StringEntry : BaseColumns {
        const val TABLE_NAME = "strings"
        const val COLUMN_TEXT = "resource"
    }

    const val SQL_CREATE_TABLE =
        "CREATE TABLE ${StringEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${StringEntry.COLUMN_TEXT} TEXT)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${StringEntry.TABLE_NAME}"
}