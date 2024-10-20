package com.example.movieapp.domain.usecase

import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.domain.repo.MoviesRepo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@ViewModelScoped
class GetMoviesUseCase @Inject constructor(private val moviesRepo: MoviesRepo) {

    operator fun invoke(): Flow<List<MovieModel?>> {

        return moviesRepo.getMoviesList()
            .zip(moviesRepo.getAllFavoritesMovies()) { allMovies, favMovies ->
                allMovies.body()?.results?.forEach { movie ->
                    movie?.isFavorite = favMovies.any { it.id == movie?.id }
                }
                return@zip allMovies.body()?.results ?: emptyList()
            }
    }
}