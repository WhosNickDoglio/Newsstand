package com.nicholasdoglio.data.local.model.feedly.authentication

data class AuthenticationActivationRequest(
    val code: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
    val grantType: String = "authorization_code"
)