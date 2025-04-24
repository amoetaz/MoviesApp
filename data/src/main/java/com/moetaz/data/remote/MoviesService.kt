package com.moetaz.data.remote

import com.moetaz.data.response.MovieDto
import retrofit2.http.GET

interface MoviesService {

    @GET("movies")
    fun getMovies() : List<MovieDto>
}