package com.movieswipe.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.movieswipe.model.Movie

@Composable
fun MovieResultScreen(movie: Movie?) {
    if (movie == null) {
        Text("No movie matched.")
        return
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(movie.title, style = MaterialTheme.typography.h5)
        Text(movie.overview ?: "")
        Text("Genres: ${movie.genres.joinToString(", ")}")
        Text("Release: ${movie.releaseDate ?: "N/A"}")
        // Add poster image if desired
    }
}
