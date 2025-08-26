package com.example.notes2025.utils

import android.util.Log

object Logger {
    private const val TAG = "Notes2025LoggerTAG"

    fun debug(contents: String) {
        Log.d(TAG, "debug: $contents")
    }
}
