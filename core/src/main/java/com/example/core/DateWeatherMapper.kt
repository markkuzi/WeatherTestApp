package com.example.core

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.text.isDigitsOnly
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface DateWeatherMapper {

    fun map(time: String, timeZone: String = "0", dateFormatId: Int = 0): String

    fun checkTime(number: String): Boolean

    fun setDateFormat(id: Int): String

    class Base : DateWeatherMapper {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun map(time: String, timeZone: String, dateFormatId: Int): String {
            return if (checkTime(time)) {
                val date = if (checkTime(timeZone)) {
                    Date((time.toLong() + timeZone.toLong()) * 1000)
                } else
                    Date(time.toLong() * 1000)
                val pattern =  setDateFormat(dateFormatId)
                val sdf = SimpleDateFormat(pattern, Locale.getDefault())
                sdf.format(date)
            } else
                "-"
        }

        override fun checkTime(number: String): Boolean {
            return (number.isDigitsOnly() && number.isNotEmpty())
        }

        override fun setDateFormat(id: Int): String {
            when(id){
                1 -> return "HH:mm"
                else -> return "dd.MM.yyyy, HH:mm"
            }
        }
    }
}