package com.ariefzuhri.gamedisc.common.util

import com.ariefzuhri.gamedisc.domain.enums.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun addDate(date: String, dateFormat: DateFormat, amount: Int): String {
    val formatter = SimpleDateFormat(dateFormat.pattern, Locale.getDefault())
    val newDate = Calendar.getInstance().apply {
        time = formatter.parse(date) as Date
        add(Calendar.DATE, amount)
    }
    return formatter.format(newDate.time)
}

fun getCurrentDate(dateFormat: DateFormat): String {
    val formatter = SimpleDateFormat(dateFormat.pattern, Locale.getDefault())
    val date = Calendar.getInstance().time
    return formatter.format(date)
}

fun reformatDate(input: String, inputFormat: DateFormat, outputFormat: DateFormat): String {
    try {
        val inputFormatter = SimpleDateFormat(inputFormat.pattern, Locale.getDefault())
        val outputFormatter = SimpleDateFormat(outputFormat.pattern, Locale.getDefault())

        val inputDate = inputFormatter.parse(input)
        if (inputDate != null) {
            return outputFormatter.format(inputDate)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return input
}