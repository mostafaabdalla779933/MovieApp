package com.example.movieapp.domain.repo

import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.model.MoviesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MoviesRepo {

     fun getMoviesList(): Flow<Response<MoviesResponse>>

     fun addMovieToFavorites(movie: MovieEntity) : Flow<Long>

     fun removeMovieFromFavorites(movie: MovieEntity) : Flow<Int>

     fun getAllFavoritesMovies(): Flow<List<MovieEntity>>
}