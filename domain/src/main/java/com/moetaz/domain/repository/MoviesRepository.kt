package com.moetaz.domain.repository

import com.moetaz.domain.models.Movie

interface MoviesRepository {

    suspend fun getMovies(): List<Movie>
}