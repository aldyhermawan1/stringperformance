package com.nbs.stringperformance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var wordController by mutableStateOf("")

    fun loadFromXml() {
        val strings = R.string.
    }
}