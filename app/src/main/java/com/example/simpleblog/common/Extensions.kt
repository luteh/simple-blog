package com.example.simpleblog.common

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Boolean?.orFalse(): Boolean = this ?: false
fun String?.or(other: String): String = if (this.isNullOrEmpty()) other else this
fun String.toFormattedDate(datePattern: String = "dd MMMM yyyy, HH:mm:ss"): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val parsedDate = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
        parsedDate.format(DateTimeFormatter.ofPattern(datePattern))
    } else {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val formatter = SimpleDateFormat(datePattern)
        formatter.format(parser.parse(this))
    }

}

fun Int?.orZero(): Int = this ?: 0

