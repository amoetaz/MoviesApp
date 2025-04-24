package com.moetaz.data.remote

import com.moetaz.data.response.MovieDto
import com.moetaz.domain.models.Movie
import com.moetaz.domain.models.Result
import com.moetaz.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


import javax.inject.Inject

fun MovieDto.toMovie() = Movie(
    id = id ,
    title = title
)
class MoviesRepositoryImp @Inject constructor(private val moviesService: MoviesService): MoviesRepository {
    override suspend fun getMovies() = flow {
        emit(Result.Loading)

        try {
            val response = moviesService.getMovies()
            emit(Result.Success(response.map { it.toMovie() }))
        }catch (e : Exception){
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)
}