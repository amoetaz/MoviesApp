package com.moetaz.domain.useCases

import com.moetaz.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val movieRepository: MoviesRepository) {

    suspend operator fun invoke(apiKey: String, movieId: Int) = movieRepository.getMovieDetail(apiKey , movieId)
}