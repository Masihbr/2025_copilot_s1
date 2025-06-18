package com.movieswipe.ui

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.movieswipe.model.Movie

@Composable
fun VotingSessionScreen(
    movies: List<Movie>,
    onVote: (String, Boolean) -> Unit,
    onFinish: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    if (currentIndex >= movies.size) {
        Text("No more movies. Waiting for results...")
        Button(onClick = onFinish) { Text("Finish Voting") }
        return
    }
    val movie = movies[currentIndex]
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount > 100) {
                        onVote(movie.id, true)
                        currentIndex++
                    } else if (dragAmount < -100) {
                        onVote(movie.id, false)
                        currentIndex++
                    }
                }
            },
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(movie.title, style = MaterialTheme.typography.h6)
            Text(movie.overview ?: "")
            // Add poster image if desired
        }
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = {
            onVote(movie.id, false)
            currentIndex++
        }) { Text("No") }
        Button(onClick = {
            onVote(movie.id, true)
            currentIndex++
        }) { Text("Yes") }
    }
}
