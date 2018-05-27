package com.nicholasdoglio.data.local.model.feedly.entities

import com.nicholasdoglio.data.util.Timestamp

data class Filters(
    val stopProcessing: Boolean?,
    val streamIds: List<String?>?,
    val searchQuery: String,
    val active: Boolean?,
    val activeUntil: Timestamp?,
    val id: String,
    val label: String
)
