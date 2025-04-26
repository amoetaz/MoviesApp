package com.moetaz.moviesapp.screens.now_playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moetaz.data.local.MoviesLocalDataSource
import com.moetaz.data.local.toMovie
import com.moetaz.domain.models.Movie
import com.moetaz.domain.useCases.GetMoviesUseCase
import com.moetaz.moviesapp.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewmodel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val localDataSource: MoviesLocalDataSource
) : ViewModel() {


    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movies: StateFlow<PagingData<Movie>> = _movies.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = PagingData.empty(),
    )


    init {
        checkLocalOrFetch()
    }
    private fun getMovies() = viewModelScope.launch {
        getMoviesUseCase()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collectLatest(_movies::emit)
    }

    private fun checkLocalOrFetch() {
        viewModelScope.launch {
            localDataSource.movies
                .first()
                .let { cachedList ->
                    if (cachedList.isNotEmpty()) {
                        val sortedList = cachedList
                            .map { it.toMovie() }
                            .sortedByDescending { it.releaseDate }

                        val paged = PagingData.from(sortedList)
                        _movies.value = paged
                    } else {
                       getMovies()
                    }
                }
        }
    }

}