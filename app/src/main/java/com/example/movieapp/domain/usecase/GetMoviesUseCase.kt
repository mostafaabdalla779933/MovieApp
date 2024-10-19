package com.example.movieapp.domain.usecase

import com.example.movieapp.data.model.MovieModel
import com.example.movieapp.domain.repo.MoviesRepo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class GetMoviesUseCase @Inject constructor(private val moviesRepo: MoviesRepo) {

    operator fun invoke(): Flow<List<MovieModel?>> {
        return moviesRepo.getMoviesList().map {
            it.body()?.results ?: emptyList()
        }
    }
}