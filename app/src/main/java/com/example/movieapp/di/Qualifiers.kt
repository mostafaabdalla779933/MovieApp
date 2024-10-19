package com.example.movieapp.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class BaseUrl


@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IODispatcher

