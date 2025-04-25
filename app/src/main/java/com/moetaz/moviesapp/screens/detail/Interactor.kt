package com.moetaz.moviesapp.screens.detail

import com.moetaz.domain.models.Movie


sealed class DetailEvents{
    data class GetMovieById(val id : Int) : DetailEvents()
}

data class DetailUiState(
    val isLoading : Boolean? = false,
    val movie : Movie? = null,
    val error : String? = null
)