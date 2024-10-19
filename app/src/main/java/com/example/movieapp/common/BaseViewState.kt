package com.example.movieapp.common



open class BaseViewState


data class ErrorState(val message: String? = null) : BaseViewState()


object Idle: BaseViewState()

open class Loader : BaseViewState() {
    data class Progress(var show: Boolean) : Loader()
}