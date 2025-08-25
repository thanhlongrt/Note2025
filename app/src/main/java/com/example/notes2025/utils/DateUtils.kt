package com.example.notes2025.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    const val FORMAT_ddMM = "dd/MM"
    const val FORMAT_ddMMyyyy_HHmm = "dd/MM/yyyy HH:mm"

    fun dateToString(
        date: Date,
        format: String = FORMAT_ddMM,
    ): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date).orEmpty()
    }

    fun dateLongToString(
        dateLong: Long,
        format: String = FORMAT_ddMM,
    ): String {
        val date = Date(dateLong)
        return dateToString(date = date, format = format)
    }

    fun stringToDate(
        dateString: String,
        format: String = FORMAT_ddMM,
    ): Date? =
        try {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            sdf.parse(dateString)
        } catch (e: Exception) {
            null
        }

    fun currentTimeStamp(): Long = System.currentTimeMillis()

    fun getDateStringToday(): String = dateToString(Date())
}
