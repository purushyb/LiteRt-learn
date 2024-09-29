package com.example.litert_learn.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.litert_learn.data.utils.Utils.loadScaledBitmap
import com.example.litert_learn.ui.viewmodels.ObjectDetectionViewModel
import java.io.File


@Composable
fun ObjectDetectionScreen(
    modifier: Modifier = Modifier,
    viewmodel: ObjectDetectionViewModel = viewModel()
) {
    val context = LocalContext.current
    var imageUri: Uri? = null

    var imageResult by rememberSaveable { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                imageResult = MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    imageUri
                )
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                imageResult = ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                    decoder.setTargetSize(300, 300)
                    decoder.isMutableRequired =
                        true // this resolve the hardware type of bitmap problem
                }
            }
            viewmodel.classify(imageResult!!)
        }
    }

    val classifierResult by viewmodel.classifierState.collectAsState()

    Column(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (imageResult == null) {
            Button(onClick = {
                val file = File(context.getExternalFilesDir(null), "image_9176593996257571918.jpg")
                imageUri = FileProvider.getUriForFile(context, "com.example.litert_learn.fileprovider", file)

                // Launch the camera to take a picture
                launcher.launch(imageUri!!)
            }) {
                Text("Capture Image")
            }
        } else {

            val imageBitmap =
                classifierResult?.image?.asImageBitmap() ?: imageResult!!.asImageBitmap()

            Image(
                bitmap = imageBitmap,
                contentDescription = "Captured image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }
}