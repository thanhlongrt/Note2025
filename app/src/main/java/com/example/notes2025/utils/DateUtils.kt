package com.example.notes2025.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    private const val DATE_FORMAT = "dd/MM/yyyy"

    fun dateToString(date: Date): String {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return sdf.format(date).orEmpty()
    }

    fun dateLongToString(dateLong: Long): String {
        val date = Date(dateLong)
        return dateToString(date)
    }

    fun stringToDate(dateString: String): Date? {
        return try {
            val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            sdf.parse(dateString)
        }catch (e: Exception) {
            null
        }
    }
}

