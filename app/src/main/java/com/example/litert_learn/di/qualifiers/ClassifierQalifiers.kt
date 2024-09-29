package com.example.litert_learn.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CatsAndDogsClassifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MobilenetClassifier