package com.example.movieapp.ui.movieslist

import com.example.movieapp.common.BaseViewModel
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.domain.usecase.FavoriteMovieUseCase
import com.example.movieapp.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesListVM @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase
) : BaseViewModel() {


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

    fun addMovieToFavorites(movie: MovieModel) {
        launch(
            request = favoriteMovieUseCase.addMovieToFavorites(movie),
            errorHandler = {
                handleException(it)
            }
        )

    }

    fun removeMovieFromFavorites(movie: MovieModel) {
        launch(
            request = favoriteMovieUseCase.removeMovieFromFavorites(movie),
            errorHandler = {
                handleException(it)
            }
        )
    }
}