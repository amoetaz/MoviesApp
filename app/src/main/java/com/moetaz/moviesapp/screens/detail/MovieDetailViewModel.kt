package com.moetaz.moviesapp.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moetaz.domain.models.Movie
import com.moetaz.domain.useCases.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> get() = _uiState

    fun emitEvent(event: DetailEvents) {
        reduce(event)
    }

    fun reduce(event: DetailEvents) {
        when (event) {
            is DetailEvents.GetMovieById -> {
                getMovieById(event.id)
            }

        }
    }

    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailUseCase.invoke(movieId = movieId, apiKey = "cc211d6ac3a05560eb808b9938cefe4e")
                .collect { result ->
                    when (result) {
                        is com.moetaz.domain.models.Result.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                        com.moetaz.domain.models.Result.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = true,
                                error = null
                            )
                        }
                        is com.moetaz.domain.models.Result.Success<Movie> -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = null,
                                movie = result.data
                            )
                        }
                    }
                }
        }
    }
}