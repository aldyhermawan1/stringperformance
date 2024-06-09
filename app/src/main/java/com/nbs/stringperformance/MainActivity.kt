package com.nbs.stringperformance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nbs.stringperformance.data.DbHelper
import com.nbs.stringperformance.ui.theme.StringperformanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dbHelper = DbHelper(this)
        val mainViewModel = MainViewModel()

        setContent {
            StringperformanceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StringScreen(
                        dbHelper =  dbHelper,
                        modifier = Modifier.padding(innerPadding),
                        viewModel = mainViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun StringScreen(
    dbHelper: DbHelper,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = MainViewModel(),
) {
    val context = LocalContext.current
    val wordController by viewModel.wordController.collectAsState()
    val timerController by viewModel.timerController.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initDb(context, dbHelper)
    }

    DisposableEffect(Unit) {
        onDispose {
            dbHelper.close()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(text = wordController)
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Time taken: $timerController")
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { viewModel.loadFromXml(context) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Load from XML Resources")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.loadFromDb(dbHelper) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Load from DB")
        }
    }
}