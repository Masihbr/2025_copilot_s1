package com.movieswipe.model

data class Movie(
    val id: String,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val genres: List<String>,
    val releaseDate: String?
)

data class Genre(
    val id: String,
    val name: String
)
