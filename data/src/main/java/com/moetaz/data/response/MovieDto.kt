package com.moetaz.data.response

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("poster_path")
    val posterPath : String,
    @SerializedName("release_date")
    val releaseDate : String,
    @SerializedName("original_language")
    val originalLanguage : String,
    @SerializedName("vote_average")
    val voteAverage : Float,
    @SerializedName("overview")
    val overview : String,
    @SerializedName("vote_count")
    val voteCount : Int,

)
