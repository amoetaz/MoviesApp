package com.moetaz.domain.repository

import androidx.paging.PagingData
import com.moetaz.domain.models.Movie
import com.moetaz.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMoviesPager(apiKey: String):  Flow<PagingData<Movie>>
}