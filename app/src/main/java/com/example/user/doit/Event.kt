package com.example.user.doit

import java.text.SimpleDateFormat
import java.util.*

data class Event(val date: Date = Date(), val title: String = "", val content: String = "", val id: Int = 0) {
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
    private val timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm")

    fun getDateString() = dateFormat.format(date)!!
    fun getTimeString() = timeFormat.format(date)!!
}