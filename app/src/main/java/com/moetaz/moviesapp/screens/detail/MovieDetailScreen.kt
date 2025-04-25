package com.moetaz.moviesapp.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.moetaz.domain.models.Movie

@Preview
@Composable
fun MovieDetialPreview(modifier: Modifier = Modifier) {
    MovieDetail(
        movie = Movie(
            id = 1,
            title = "Movie Title",
            posterUrl = "https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrF",
            releaseDate = "",
            originalLanguage = "",
            overview = "",
            voteAverage = 0.0f,
            voteCount = 0
        )
    )
}

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.emitEvent(DetailEvents.GetMovieById(movieId))
    }

    Box (modifier = Modifier.fillMaxSize()){

        uiState.value.movie?.let {
            MovieDetail(it)

        }

        uiState.value.isLoading?.let {
            if (it) {
                FullScreenLoading()
            }
        }
        uiState.value.error?.let {
            FullScreenError(it) {
                viewModel.emitEvent(DetailEvents.GetMovieById(movieId))
            }
        }
    }
}

@Composable
fun MovieDetail(movie: Movie) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterUrl}",
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Released: ${movie.releaseDate}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Language: ${movie.originalLanguage.uppercase()}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Rating",
                tint = Color(0xFFFFC107)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${movie.voteAverage} (${movie.voteCount} votes)",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 22.sp
        )
    }
}

@Composable
fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Optional background color
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun FullScreenError(message: String, onRetry: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                color = Color.Red,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
            onRetry?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = it) {
                    Text("Retry")
                }
            }
        }
    }
}