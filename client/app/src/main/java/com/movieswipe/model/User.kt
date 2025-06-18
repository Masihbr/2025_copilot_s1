package com.movieswipe.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String?,
    val groups: List<String>,
    val preferences: List<String>
)
