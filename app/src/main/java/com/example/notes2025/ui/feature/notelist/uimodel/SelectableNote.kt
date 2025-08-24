package com.example.notes2025.ui.feature.notelist.uimodel

import com.example.notes2025.utils.DateUtils

data class SelectableNote(
    val id: String,
    val title: String = "",
    val contents: String = "",
    val lastEdit: Long = 0,
    val isSelected: Boolean = false,
) {
    val lastEditStr: String = DateUtils.dateLongToString(lastEdit)
}
