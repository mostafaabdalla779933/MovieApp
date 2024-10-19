package com.example.movieapp.data.mapper

import com.example.movieapp.data.model.MovieEntity
import com.example.movieapp.data.model.MovieModel


fun MovieModel.toEntity() : MovieEntity {
    return MovieEntity(
        id = this.id ?: 0,
        posterPath = this.posterPath,
        title = this.title,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        overview = this.overview,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        video = this.video,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        popularity = this.popularity,
        adult = this.adult,
    )
}