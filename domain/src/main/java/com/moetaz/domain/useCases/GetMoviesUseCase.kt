package com.moetaz.domain.useCases

import com.moetaz.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val movieRepository: MoviesRepository) {

    suspend fun invoke() = movieRepository.getMovies()
}