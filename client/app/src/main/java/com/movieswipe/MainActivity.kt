package com.movieswipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import com.movieswipe.ui.GoogleSignInScreen
import com.movieswipe.ui.GroupManagementScreen
import com.movieswipe.ui.GenrePreferenceScreen
import com.movieswipe.ui.VotingSessionScreen
import com.movieswipe.ui.MovieResultScreen
import com.movieswipe.ui.theme.MovieSwipeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieSwipeTheme {
                // TODO: Implement navigation and state management
                // Example: Show GoogleSignInScreen, then GroupManagementScreen, etc.
                // GoogleSignInScreen(context = this, onSignInSuccess = { /* ... */ }, backendUrl = "http://10.0.2.2:4000/")
            }
        }
    }
}
