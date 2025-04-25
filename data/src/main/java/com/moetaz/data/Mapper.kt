package com.moetaz.data

import com.moetaz.data.response.MovieDto
import com.moetaz.domain.models.Movie


fun MovieDto.toMovie() = Movie(
    id = id ,
    title = originalTitle ,
    posterUrl = "http://image.tmdb.org/t/p/w185$posterPath",
    releaseDate = releaseDate,
    originalLanguage = originalLanguage,
    overview = overview,
    voteAverage = voteAverage,
    voteCount = voteCount,

    )