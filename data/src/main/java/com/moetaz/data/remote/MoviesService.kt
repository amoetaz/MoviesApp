package com.moetaz.data.remote

import com.moetaz.data.response.MovieDto
import com.moetaz.data.response.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/now_playing")
    suspend fun getPagingMovies(@Query("api_key") apiKey: String , @Query("page") page: Int): MoviesResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
    ): MovieDto
}