package com.moetaz.domain.repository

import androidx.paging.PagingData
import com.moetaz.domain.models.Movie
import com.moetaz.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMoviesPager():  Flow<PagingData<Movie>>

    suspend fun getMovieDetail(apiKey: String , movieId: Int): Flow<Result<Movie>>
}