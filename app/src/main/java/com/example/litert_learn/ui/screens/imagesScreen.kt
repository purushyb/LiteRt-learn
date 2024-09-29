package com.example.litert_learn.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.litert_learn.ui.viewmodels.MainViewModel
import com.example.litert_learn.data.PreviewDataSource
import com.example.litert_learn.data.utils.Utils
import com.example.litert_learn.ui.screens.widgets.ImageListWidget

@Composable
fun ImageScreen(modifier: Modifier = Modifier, viewmodel: MainViewModel = viewModel()) {
    val context = LocalContext.current
    val title by viewmodel.classifierState.collectAsState()

    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
    ) {
        Text(title, style = MaterialTheme.typography.displaySmall)
        ImageListWidget(images = viewmodel.imagesList) {
            viewmodel.classify(Utils.drawableToBitmap(context, it)!!)
        }
    }
}