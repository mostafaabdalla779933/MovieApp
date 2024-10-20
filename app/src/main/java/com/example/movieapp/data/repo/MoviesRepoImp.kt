package com.example.movieapp.data.repo

import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.remote.ApiService
import com.example.movieapp.di.IODispatcher
import com.example.movieapp.domain.repo.MoviesRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class MoviesRepoImp @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    @IODispatcher val coroutineDispatcher: CoroutineDispatcher
) : MoviesRepo {

    override fun getMoviesList() = flow {
        emit(apiService.getMoviesList())
    }.flowOn(coroutineDispatcher)

    override fun addMovieToFavorites(movie: MovieEntity): Flow<Long> {
        return flow {
            emit(movieDao.addMovieItem(movie))
        }.flowOn(coroutineDispatcher)

    }

    override fun removeMovieFromFavorites(movie: MovieEntity): Flow<Int> {
        return flow {
            emit(movieDao.deleteMovieById(movie.id))
        }.flowOn(coroutineDispatcher)

    }

    override fun getAllFavoritesMovies() = movieDao.getAllMovies().flowOn(coroutineDispatcher)
}