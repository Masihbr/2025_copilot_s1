package com.movieswipe.model

data class VotingSession(
    val id: String,
    val group: String,
    val movies: List<String>,
    val votes: List<Vote>,
    val startedAt: String,
    val endedAt: String?
)

data class Vote(
    val user: String,
    val movie: String,
    val vote: Boolean
)
