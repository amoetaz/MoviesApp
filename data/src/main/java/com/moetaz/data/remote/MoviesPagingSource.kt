package com.moetaz.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moetaz.data.BuildConfig
import com.moetaz.data.local.MoviesLocalDataSource
import com.moetaz.data.local.toMovieEntity
import com.moetaz.data.toMovie
import com.moetaz.domain.models.Movie

class MoviesPagingSource(
    private val localDataSource: MoviesLocalDataSource,
    private val service: MoviesService,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            val response = service.getPagingMovies(BuildConfig.API_KEY, currentPage)

      /*      if (currentPage == 1)
                localDataSource.deleteAll()

            localDataSource.insertAll(response.results.map { it.toMovie().toMovieEntity() })*/
            LoadResult.Page(
                data = response.results.map { it.toMovie() },
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (currentPage < response.totalPages) currentPage + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
