package com.moetaz.domain.models

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val releaseDate: String,
    val originalLanguage: String,
    val overview: String,
    val voteAverage: Float,
    val voteCount: Int,
)
