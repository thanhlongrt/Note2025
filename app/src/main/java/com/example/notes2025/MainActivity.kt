package com.example.notes2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.notes2025.data.DummyDataProvider
import com.example.notes2025.ui.feature.notelist.NoteListScreen
import com.example.notes2025.ui.theme.Notes2025Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Notes2025Theme(
                darkTheme = false,
                dynamicColor = false,
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NoteListScreen(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
