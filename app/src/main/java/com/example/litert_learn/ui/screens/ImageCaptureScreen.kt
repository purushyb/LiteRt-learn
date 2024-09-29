package com.example.litert_learn.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.litert_learn.ui.viewmodels.ImageCaptureViewModel

@Composable
fun ImageCaptureScreen(
    modifier: Modifier = Modifier,
    viewModel: ImageCaptureViewModel = viewModel(),
) {

    var imageResult by rememberSaveable { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        it?.let {
            imageResult = it
            viewModel.classify(it)
        }
    }

    val classifierResult by viewModel.classifierState.collectAsState()

    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (imageResult == null) {
            Button(onClick = { launcher.launch(null) }) {
                Text("Capture Image")
            }
        } else {
            Image(
                bitmap = imageResult!!.asImageBitmap(),
                contentDescription = "Captured image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Text(classifierResult, style = MaterialTheme.typography.titleLarge)
        }
    }
}