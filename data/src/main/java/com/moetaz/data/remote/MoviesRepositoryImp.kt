package com.moetaz.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.moetaz.data.response.MovieDto
import com.moetaz.domain.models.Movie
import com.moetaz.domain.repository.MoviesRepository


import javax.inject.Inject

fun MovieDto.toMovie() = Movie(
    id = id ,
    title = originalTitle ,
    posterUrl = "http://image.tmdb.org/t/p/w185$posterPath"
)
class MoviesRepositoryImp @Inject constructor(private val moviesService: MoviesService): MoviesRepository {
    override suspend fun getMoviesPager(apiKey: String) =  Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviesPagingSource(moviesService, apiKey) }
    ).flow
}