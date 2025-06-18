package com.movieswipe.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.movieswipe.data.TokenManager
import com.movieswipe.network.ApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun GoogleSignInScreen(
    context: Context,
    onSignInSuccess: (String) -> Unit,
    backendUrl: String
) {
    val tokenManager = remember { TokenManager(context) }
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl(backendUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api = remember { retrofit.create(ApiService::class.java) }
    val coroutineScope = rememberCoroutineScope()
    var error by remember { mutableStateOf<String?>(null) }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("YOUR_WEB_CLIENT_ID") // Replace with your client ID
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            coroutineScope.launch {
                val response = api.googleAuth(mapOf("idToken" to (account.idToken ?: "")))
                if (response.isSuccessful) {
                    val token = response.body()?.token ?: ""
                    tokenManager.saveToken(token)
                    onSignInSuccess(token)
                } else {
                    error = "Backend auth failed"
                }
            }
        } catch (e: Exception) {
            error = "Google sign-in failed"
        }
    }

    // UI: Button to trigger sign-in
    // ... Compose UI code for sign-in button and error display ...
}
