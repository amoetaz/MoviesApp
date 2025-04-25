package com.moetaz.domain.useCases

import com.moetaz.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val movieRepository: MoviesRepository) {

    suspend operator fun invoke() = movieRepository.getMoviesPager("cc211d6ac3a05560eb808b9938cefe4e")
}