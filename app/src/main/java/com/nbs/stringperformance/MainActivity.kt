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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nbs.stringperformance.ui.theme.StringperformanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StringperformanceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StringScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun StringScreen(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp),
    ) {
        Text(text = "String resources")
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Load from XML Resources")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Load from DB")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StringScreenPreview() {
    StringperformanceTheme {
        StringScreen()
    }
}