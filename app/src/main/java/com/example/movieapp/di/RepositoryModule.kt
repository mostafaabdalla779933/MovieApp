package com.example.movieapp.di


import com.example.movieapp.data.repo.MoviesRepoImp
import com.example.movieapp.domain.repo.MoviesRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MovieRepositoryModule {

    @Binds
    abstract fun bindMovieRepository(
        moviesRepoImp: MoviesRepoImp
    ): MoviesRepo
}
