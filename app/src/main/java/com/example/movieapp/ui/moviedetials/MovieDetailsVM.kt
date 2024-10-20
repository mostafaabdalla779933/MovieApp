package com.example.movieapp.ui.moviedetials

import com.example.movieapp.common.BaseViewModel
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.domain.usecase.FavoriteMovieUseCase
import com.example.movieapp.ui.movieslist.MoviesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsVM @Inject constructor(private val favoriteMovieUseCase: FavoriteMovieUseCase) :
    BaseViewModel() {


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