package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.data.local.MovieAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMoviesAppDatabase(
        @ApplicationContext applicationContext: Context,
    ): MovieAppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            MovieAppDatabase::class.java,
            "movies_app.db"
        ).build()
    }

    @Provides
    fun provideMoviesDao(appDatabase: MovieAppDatabase): MovieDao =
        appDatabase.movieDao()

}