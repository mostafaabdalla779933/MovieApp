package com.example.movieapp.data.repo

import com.example.movieapp.data.remote.ApiService
import com.example.movieapp.di.IODispatcher
import com.example.movieapp.domain.repo.MoviesRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class MoviesRepoImp @Inject constructor(
    private val apiService: ApiService,
    @IODispatcher val coroutineDispatcher: CoroutineDispatcher
) : MoviesRepo {

    override fun getMoviesList() = flow {
        emit(apiService.getMoviesList())
    }.flowOn(coroutineDispatcher)
}