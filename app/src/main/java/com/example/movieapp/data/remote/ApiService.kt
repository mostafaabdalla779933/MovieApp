package com.example.movieapp.data.remote

import com.example.movieapp.data.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(GET_MOVIES_API)
    suspend fun getMoviesList(): Response<MoviesResponse>

    companion object {
        const val GET_MOVIES_API = "3/discover/movie?primary_release_year=2024&sort_by=vote_average.desc&api_key=654feb9e72d505cea21fc3453103024f"
    }
}