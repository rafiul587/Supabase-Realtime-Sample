package com.example.supabaserealtime

import kotlinx.serialization.Serializable


@Serializable
data class Message(
    val id: Int = 0,
    val content: String
)