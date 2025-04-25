package com.moetaz.moviesapp.screens.now_playing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.moetaz.domain.models.Movie

@Preview
@Composable
fun MovieItemPreview(modifier: Modifier = Modifier) {
    MovieItem(
        movie = Movie(
            id = 1,
            title = "Movie Title",
            posterUrl = "https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrF",
            releaseDate = "",
            originalLanguage = "",
            overview = "",
            voteAverage = 0.0f,
            voteCount = 0
        ),
        )
}

@Composable
fun MovieItem(movie: Movie , onItemClick: (Int) -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(150.dp)
            .wrapContentHeight()
            .clickable { onItemClick(movie.id) }
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            movie.title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}
