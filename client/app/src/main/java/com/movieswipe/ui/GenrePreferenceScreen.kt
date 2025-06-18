package com.movieswipe.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.movieswipe.model.Genre

@Composable
fun GenrePreferenceScreen(
    genres: List<Genre>,
    selected: List<String>,
    onSelect: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Select Your Favorite Genres", style = MaterialTheme.typography.h6)
        genres.forEach { genre ->
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Checkbox(
                    checked = selected.contains(genre.id),
                    onCheckedChange = { onSelect(genre.id) }
                )
                Text(genre.name)
            }
        }
        Button(onClick = onSave, enabled = selected.isNotEmpty()) { Text("Save Preferences") }
    }
}
