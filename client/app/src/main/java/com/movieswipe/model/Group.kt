package com.movieswipe.model

data class Group(
    val id: String,
    val name: String,
    val owner: String,
    val members: List<String>,
    val inviteCode: String,
    val activeSession: String?
)
