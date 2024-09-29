package com.example.litert_learn.di.modules

import android.content.Context
import com.example.litert_learn.data.CatsNDogsClassifier
import com.example.litert_learn.data.MobileNetClassifier
import com.example.litert_learn.data.definations.Classifier
import com.example.litert_learn.data.ObjectDetectionClassifier
import com.example.litert_learn.di.qualifiers.CatsAndDogsClassifier
import com.example.litert_learn.di.qualifiers.MobilenetClassifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ClassifierModules {

    @CatsAndDogsClassifier
    @Provides
    fun bindCatsVsDogsClassifier(
        @ApplicationContext context: Context
    ): CatsNDogsClassifier {
        return CatsNDogsClassifier(
            assetManager = context.assets,
            context = context,
            inputSize = 224,
            modelPath = "converted_model.tflite",
            labelPath = "label.txt",
        )
    }

    @MobilenetClassifier
    @Provides
    fun bindMobilenetClassifier(
        @ApplicationContext context: Context
    ): MobileNetClassifier {
        return MobileNetClassifier(
            assetManager = context.assets,
            context = context,
            inputSize = 224,
            modelPath = "mobilenet_v1_1.0_224_quant.tflite",
            labelPath = "labels_mobilenet_quant_v1_224.txt",
        )
    }

    @Provides
    fun bindObjectDetectionAPIModel(@ApplicationContext context: Context): ObjectDetectionClassifier {
        return ObjectDetectionClassifier.create(
            context = context,
            assetManager = context.assets,
            modelFilename = "detect.tflite",
            labelFilename = "file:///android_asset/labelmap.txt",
            inputSize = 300,
            isQuantized = true
        )
    }
}