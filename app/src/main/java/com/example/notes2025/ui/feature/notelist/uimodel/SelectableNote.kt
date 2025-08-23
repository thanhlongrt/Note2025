package com.example.notes2025.ui.feature.notelist.uimodel

data class SelectableNote(
    val id: String,
    val title: String,
    val contents: String = "",
    val isSelected: Boolean = false,
)
