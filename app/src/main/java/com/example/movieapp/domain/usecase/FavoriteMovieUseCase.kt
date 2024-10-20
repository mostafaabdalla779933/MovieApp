package com.example.movieapp.domain.usecase

import com.example.movieapp.data.mapper.toEntity
import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.domain.repo.MoviesRepo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@ViewModelScoped
class FavoriteMovieUseCase @Inject constructor(private val moviesRepo: MoviesRepo) {

    fun addMovieToFavorites(movie: MovieModel): Flow<Long> {
        return moviesRepo.addMovieToFavorites(movie.toEntity())

    }

    fun removeMovieFromFavorites(movie: MovieModel): Flow<Int> {
        return moviesRepo.removeMovieFromFavorites(movie.toEntity())
    }

}