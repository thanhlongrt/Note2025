package com.example.notes2025.model

data class Note(
    val id: String,
    val title: String,
    val contents: String,
    val lastEdit: Long,
)
