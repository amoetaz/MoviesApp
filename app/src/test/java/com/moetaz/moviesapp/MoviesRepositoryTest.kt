package com.moetaz.moviesapp

import android.util.Log
import com.moetaz.data.remote.MoviesRepositoryImp
import com.moetaz.data.remote.MoviesService
import com.moetaz.data.response.MovieDto
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MoviesRepositoryTest {

    private lateinit var repository: MoviesRepositoryImp
    private lateinit var moviesService: MoviesService

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        moviesService = mockk()
        repository = MoviesRepositoryImp(moviesService)
    }

    @Test
    fun `validate API key returns success`() = runTest(testDispatcher) {

        val apiKey = "test_api_key"
        val movieId = 123


        val fakeResponse = MovieDto(
            id = movieId,
            originalTitle = "Fake Movie",
            overview = "Some overview",
            posterPath = "/poster.jpg",
            releaseDate = "2024-01-01",
            voteAverage = 8.5f,
            originalLanguage = "en", voteCount = 5,

            )


        coEvery { moviesService.getMovieDetail(movieId, apiKey) } returns fakeResponse


        val result = repository.getMovieDetail(apiKey, movieId).first()
        Log.d("Result ="," $result")

        assertTrue(result is com.moetaz.domain.models.Result.Success)
        val movie = (result as com.moetaz.domain.models.Result.Success).data
        assertEquals(movie.id, fakeResponse.id)
        assertEquals(movie.title, fakeResponse.originalTitle)
    }
}
