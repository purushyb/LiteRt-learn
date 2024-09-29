package com.example.litert_learn.ui.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.litert_learn.R
import com.example.litert_learn.data.CatsNDogsClassifier
import com.example.litert_learn.di.qualifiers.CatsAndDogsClassifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @CatsAndDogsClassifier private val catsNDogsClassifier: CatsNDogsClassifier
): ViewModel() {

    private val _classifierState = MutableStateFlow<String>("Select a image to classify")
    val classifierState = _classifierState.asStateFlow()

    val imagesList = listOf<Int>(
        R.drawable.img0,
        R.drawable.img1,
        R.drawable.img17,
        R.drawable.img2,
        R.drawable.img22,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5,
        R.drawable.img6
    )

    fun classify(bitmap: Bitmap) {
        viewModelScope.launch {
            catsNDogsClassifier.recognizeImage(bitmap).get(0).title.let {
                _classifierState.value = it
            }
        }
    }

}