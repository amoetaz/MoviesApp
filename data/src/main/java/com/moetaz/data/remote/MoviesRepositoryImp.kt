package com.moetaz.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.moetaz.data.local.MoviesLocalDataSource
import com.moetaz.data.response.MovieDto
import com.moetaz.data.toMovie
import com.moetaz.domain.models.Movie
import com.moetaz.domain.models.Result
import com.moetaz.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


import javax.inject.Inject

class MoviesRepositoryImp @Inject constructor(
    private val moviesService: MoviesService,
    private val localDataSource: MoviesLocalDataSource
) :
    MoviesRepository {
    override suspend fun getMoviesPager() = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviesPagingSource(localDataSource,moviesService) }
    ).flow

    override suspend fun getMovieDetail(apiKey: String, movieId: Int): Flow<Result<Movie>> {
        return flow {
            emit(Result.Loading)

            try {
                val response = moviesService.getMovieDetail(movieId, apiKey)
                val movie = response.toMovie()
                emit(Result.Success(movie))
            } catch (e: Exception) {
                emit(Result.Error(e.message))
            }

        }.flowOn(Dispatchers.IO)
    }
}