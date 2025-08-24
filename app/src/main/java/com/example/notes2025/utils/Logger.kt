package com.example.notes2025.utils

import android.util.Log

object Logger {
    private val TAG = "Notes2025Logger"

    fun debug(contents: String) {
        Log.d(TAG, "debug: $contents")
    }
}
