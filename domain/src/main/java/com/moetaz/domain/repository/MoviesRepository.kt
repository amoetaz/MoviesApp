package com.moetaz.domain.repository

import com.moetaz.domain.models.Movie
import com.moetaz.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMovies(): Flow<Result<List<Movie>>>
}