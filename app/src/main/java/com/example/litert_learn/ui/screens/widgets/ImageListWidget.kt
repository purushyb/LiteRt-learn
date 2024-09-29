package com.example.litert_learn.ui.screens.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.litert_learn.data.PreviewDataSource

@Composable
fun ImageListWidget(images: List<Int>, classify: (Int) -> Unit) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        items(images) {
            val paintImage = painterResource(id = it)
            Image(
                painter = paintImage,
                contentDescription = it.toString(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(2F)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        classify(it)
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageListWidgetPreview() {
    ImageListWidget(PreviewDataSource.imagesList) {}
}