package com.example.litert_learn.ui.models.uistate

import android.graphics.Bitmap

data class ObjectDetectionUiState(
    val image: Bitmap? = null,
    val loading: Boolean = false,
)
