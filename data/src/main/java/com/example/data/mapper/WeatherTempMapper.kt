package com.example.data.mapper

import com.example.core.Mapper
import java.text.DecimalFormat

interface WeatherTempMapper: Mapper<String, String> {

    class Base : WeatherTempMapper {
        override fun map(source: String): String {
            try {
                val temp = DecimalFormat("#.#").format(source.toDouble())
                return if (temp.toDouble() > 0) "+$temp°C" else "$temp°C"
            } catch (e: Exception) {
                return "-"
            }
        }
    }
}