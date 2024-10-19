package com.example.movieapp.ui.movieslist

import com.example.movieapp.common.BaseViewState
import com.example.movieapp.data.model.MovieModel

sealed class MoviesViewState : BaseViewState() {

    class MoviesList(val list: List<MovieModel?>) : MoviesViewState()

}