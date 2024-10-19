package com.example.movieapp.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException


open class BaseViewModel : ViewModel() {


    protected var _internalState = MutableStateFlow(BaseViewState())
    var internalState = _internalState.asStateFlow()
    private var _errorLiveData = SingleLiveEvent<ErrorState>()
    var errorLiveData = _errorLiveData
    private val jobs = SupervisorJob()

    fun <T> launch(
        request: Flow<T>,
        successHandler: suspend (T) -> Unit,
        errorHandler: suspend (Throwable?) -> Unit,
        loadingHandler: suspend (Boolean) -> Unit = { _internalState.value = Loader.Progress(it) }
    ) {
        val job = viewModelScope.launch {
            request.launchRequest(
                successHandler = successHandler,
                errorHandler = errorHandler,
                loadingHandler = loadingHandler
            )

        }
        jobs.children.plus(job)
    }

    fun handleException(throwable: Throwable?) {
        throwable?.printStackTrace()
        when (throwable) {
            is UnknownHostException,is HttpException  -> {
                _errorLiveData.value = ErrorState(message = "connection error")
            }
            else -> {
                _errorLiveData.value = ErrorState()
            }
        }
    }

    fun setStateIdle() {
        _internalState.value = Idle
    }

    override fun onCleared() {
        super.onCleared()
        jobs.cancelChildren()
    }
}


suspend fun <T> Flow<T>.launchRequest(
    successHandler: suspend (T) -> Unit,
    errorHandler: suspend (Throwable?) -> Unit,
    loadingHandler: suspend (Boolean) -> Unit
) {
    onStart {
        loadingHandler(true)
    }.catch {
        errorHandler(it)
    }.onCompletion {
        loadingHandler(false)
    }.collect {
        successHandler(it)
    }
}