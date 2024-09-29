package com.example.litert_learn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.litert_learn.ui.screens.ImageCaptureScreen
import com.example.litert_learn.ui.screens.ObjectDetectionScreen
import com.example.litert_learn.ui.theme.LiteRtlearnTheme
import com.example.litert_learn.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiteRtlearnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    ImageScreen(modifier = Modifier.padding(innerPadding))
//                    ImageCaptureScreen(modifier = Modifier.padding(innerPadding))
                    ObjectDetectionScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}