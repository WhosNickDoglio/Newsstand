package dev.whosnickdoglio.feedly.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val errorMessage: String,
    val errorId: String,
)
