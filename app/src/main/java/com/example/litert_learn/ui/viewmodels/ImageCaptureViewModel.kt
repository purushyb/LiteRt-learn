package com.example.litert_learn.ui.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.litert_learn.data.MobileNetClassifier
import com.example.litert_learn.di.qualifiers.MobilenetClassifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageCaptureViewModel @Inject constructor(
    @MobilenetClassifier val mobileNetClassifier: MobileNetClassifier
): ViewModel() {

    private val _classifierState = MutableStateFlow<String>("Running classifier")
    val classifierState = _classifierState.asStateFlow()

    fun classify(bitmap: Bitmap) {
        viewModelScope.launch {
            var result = ""
            mobileNetClassifier.recognizeImage(bitmap).forEach{
                result += "${it.title}\n"
            }
            _classifierState.value = result
        }
    }
}