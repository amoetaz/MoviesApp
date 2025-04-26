package com.moetaz.moviesapp.screens.now_playing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.work.ListenableWorker.Result.retry
import com.moetaz.domain.models.Movie
import com.moetaz.moviesapp.screens.detail.FullScreenError
import com.moetaz.moviesapp.screens.detail.FullScreenLoading

@Composable
fun MoviesListScreen(
    nowPlayingViewmodel: NowPlayingViewmodel = hiltViewModel(),
    onItemClick: (Int) -> Unit
) {
    val nowPlayingMovies = nowPlayingViewmodel.movies.collectAsLazyPagingItems()

    MovieGridScreen(movies = nowPlayingMovies, onItemClick = onItemClick)
}

@Composable
fun MovieGridScreen(movies: LazyPagingItems<Movie>, onItemClick: (Int) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredMovies = remember(searchQuery, movies.itemSnapshotList.items) {
        if (searchQuery.isBlank()) null
        else movies.itemSnapshotList.items.filter {
            it.title.contains(searchQuery, ignoreCase = true)
        }
    }

    Box( modifier = Modifier
        .fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search movies") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (filteredMovies != null) {
                    // When searching: use filtered list (local)
                    items(filteredMovies.size) { index ->
                        MovieItem(movie = filteredMovies[index], onItemClick = onItemClick)
                    }

                    if (filteredMovies.isEmpty()) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Text(
                                "No movies found.",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {

                    items(movies.itemCount) { index ->
                        movies[index]?.let { MovieItem(movie = it, onItemClick = onItemClick) }
                    }

                }

                movies.apply {
                    when {
                        loadState.append is LoadState.Loading -> {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val e = loadState.append as LoadState.Error
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                ErrorItem(message = e.error.localizedMessage ?: "Unknown Error") {
                                    retry()
                                }
                            }
                        }
                    }
                }
            }
        }

        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    FullScreenLoading()
                }
                loadState.refresh is LoadState.Error -> {
                    val e = loadState.refresh as LoadState.Error
                    FullScreenError(
                        message = e.error.localizedMessage ?: "Unknown Error",
                        onRetry = { retry() }
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorItem(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
