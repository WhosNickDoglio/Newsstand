package com.nicholasdoglio.data.local.model.feedly.entities

data class Stream(
    val id: String,
    val continuation: String?,
    val title: String?,
    val items: List<Entry>
)