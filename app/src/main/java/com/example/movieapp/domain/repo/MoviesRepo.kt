package com.example.movieapp.domain.repo

import com.example.movieapp.data.model.MoviesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MoviesRepo {

     fun getMoviesList(): Flow<Response<MoviesResponse>>
}