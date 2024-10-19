package com.example.movieapp.ui.movieslist

import com.example.movieapp.common.BaseViewModel
import com.example.movieapp.domain.usecase.GetMoviesUseCase
import com.example.movieapp.ui.movieslist.MoviesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesListVM @Inject constructor(val getMoviesUseCase: GetMoviesUseCase) : BaseViewModel() {


    fun getMoviesList() {
        launch(
            request = getMoviesUseCase(),
            successHandler = {
                _internalState.value = MoviesViewState.MoviesList(it)
            },
            errorHandler = {
                handleException(it)
            }
        )
    }
}