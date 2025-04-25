package com.moetaz.moviesapp.screens.now_playing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.moetaz.domain.models.Movie

@Composable
fun MoviesListScreen(
    nowPlayingViewmodel: NowPlayingViewmodel = hiltViewModel(),
    onItemClick: (Int) -> Unit
) {
    val nowPlayingMovies = nowPlayingViewmodel.movies.collectAsLazyPagingItems()

    MovieGridScreen(movies = nowPlayingMovies , onItemClick = onItemClick)
}

@Composable
fun MovieGridScreen(movies: LazyPagingItems<Movie> , onItemClick: (Int) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredMovies = remember(searchQuery, movies.itemSnapshotList.items) {
        if (searchQuery.isBlank()) null
        else movies.itemSnapshotList.items.filter {
            it.title.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search movies") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (filteredMovies != null) {
                // When searching: use filtered list (local)
                items(filteredMovies.size) { index ->
                    MovieItem(movie = filteredMovies[index] , onItemClick = onItemClick)
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
                    movies[index]?.let { MovieItem(movie = it  , onItemClick = onItemClick) }
                }

            }
        }
    }
}
