package com.example.core

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

interface DateWeatherMapper {

    fun map(time: String, timeZone: String = "0", dateFormatId: Int = 0): String

    fun setDateFormat(id: Int): String

    class Base : DateWeatherMapper {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun map(time: String, timeZone: String, dateFormatId: Int): String {
            return try {
                val currentTimeZone = TimeZone.getDefault()
                val offsetInMillis = currentTimeZone.rawOffset / 1000
                val date = Date((time.toLong() + timeZone.toLong() - offsetInMillis) * 1000)
                val pattern =  setDateFormat(dateFormatId)
                val sdf = SimpleDateFormat(pattern, Locale.getDefault())
                sdf.format(date)
            } catch (e:Exception){
                "-"
            }
        }

        override fun setDateFormat(id: Int): String {
            when(id){
                1 -> return "HH:mm"
                else -> return "dd.MM.yyyy, HH:mm"
            }
        }
    }
}