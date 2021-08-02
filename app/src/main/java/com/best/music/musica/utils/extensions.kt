package com.best.music.musica.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate():String{
    val pattern = "E dd MMMM, yyyy hh:mma"
    val sdf = SimpleDateFormat(pattern)
    val date = Date().apply {
        time = this@toDate
    }
    return sdf.format(date)
}

fun Long.toSimpleDate():String{
    val pattern = "dd MMM, yyyy hh:mma"
    val sdf = SimpleDateFormat(pattern)
    val date = Date().apply {
        time = this@toSimpleDate
    }
    return sdf.format(date)
}