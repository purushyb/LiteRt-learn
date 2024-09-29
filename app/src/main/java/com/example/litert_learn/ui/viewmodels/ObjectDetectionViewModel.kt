package com.example.litert_learn.ui.viewmodels

import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.graphics.YuvImage
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.litert_learn.data.ObjectDetectionClassifier
import com.example.litert_learn.data.utils.Utils
import com.example.litert_learn.data.utils.Utils.bitmapToYuvBytes
import com.example.litert_learn.ui.models.uistate.ObjectDetectionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ObjectDetectionViewModel @Inject constructor(
    private val classifier: ObjectDetectionClassifier
): ViewModel() {
    private val _classifierState = MutableStateFlow<ObjectDetectionUiState?>(null)
    val classifierState = _classifierState.asStateFlow()

    fun classify(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.Default) {
            var resultBitMap = bitmap
            _classifierState.value = ObjectDetectionUiState(loading = true)
            classifier.recognizeImage(resultBitMap).let {
                it.forEach {result ->
                    if(result.confidence > MIN_CONFIDENCE){
                        resultBitMap = Utils.drawRectangleOnBitmap(
                            bitmap = resultBitMap,
                            location = result.location,
                            title = result.title ?: "No Title"
                        )
                    }
                }
                _classifierState.value = ObjectDetectionUiState(resultBitMap, false)
            }
        }
    }

    companion object{
        private const val MIN_CONFIDENCE = 0.4f
        private const val INPUT_SIZE = 300
    }
}