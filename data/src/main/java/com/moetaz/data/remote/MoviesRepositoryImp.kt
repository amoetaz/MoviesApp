package com.moetaz.data.remote

import com.moetaz.domain.models.Movie
import com.moetaz.domain.repository.MoviesRepository

class MoviesRepositoryImp : MoviesRepository {
    override suspend fun getMovies(): List<Movie> {
        TODO("Not yet implemented")
    }
}