package com.example.data.mapper

import com.example.core.Mapper
import java.text.DecimalFormat

interface WeatherTempMapper: Mapper<String, String> {

    class Base : WeatherTempMapper {
        override fun map(source: String): String {
            try {
                val temp = source.toDouble().toInt()
                return if (temp > 0) "+$temp°C" else "$temp°C"
            } catch (e: Exception) {
                return "-"
            }
        }
    }
}