package com.nicholasdoglio.data.local.model.feedly.authentication

data class AuthenticationRefreshRequest(
    val refreshToken: String,
    val clientId: String,
    val clientSecret: String,
    val grantType: String = "refresh_token"
)