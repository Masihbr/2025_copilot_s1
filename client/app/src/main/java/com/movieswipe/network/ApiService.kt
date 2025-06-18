package com.movieswipe.network

import com.movieswipe.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("/auth/google")
    suspend fun googleAuth(@Body body: Map<String, String>): Response<AuthResponse>

    @GET("/groups")
    suspend fun getGroups(@Header("Authorization") token: String): Response<List<Group>>

    @POST("/groups")
    suspend fun createGroup(@Header("Authorization") token: String, @Body body: Map<String, String>): Response<Group>

    @POST("/groups/join")
    suspend fun joinGroup(@Header("Authorization") token: String, @Body body: Map<String, String>): Response<Group>

    @DELETE("/groups/{id}")
    suspend fun deleteGroup(@Header("Authorization") token: String, @Path("id") id: String): Response<Unit>

    @GET("/genres")
    suspend fun getGenres(): Response<List<Genre>>

    @GET("/preferences")
    suspend fun getPreferences(@Header("Authorization") token: String): Response<PreferencesResponse>

    @PUT("/preferences")
    suspend fun setPreferences(@Header("Authorization") token: String, @Body body: PreferencesRequest): Response<PreferencesResponse>

    @POST("/voting/start")
    suspend fun startVoting(@Header("Authorization") token: String, @Body body: Map<String, String>): Response<VotingSession>

    @POST("/voting/vote")
    suspend fun vote(@Header("Authorization") token: String, @Body body: VoteRequest): Response<Unit>

    @POST("/voting/end")
    suspend fun endVoting(@Header("Authorization") token: String, @Body body: Map<String, String>): Response<MovieResultResponse>
}

// DTOs

data class AuthResponse(val token: String, val user: User)
data class PreferencesRequest(val preferences: List<String>)
data class PreferencesResponse(val preferences: List<String>)
data class VoteRequest(val sessionId: String, val movieId: String, val vote: Boolean)
data class MovieResultResponse(val winner: String?, val movieDetails: Movie?)
