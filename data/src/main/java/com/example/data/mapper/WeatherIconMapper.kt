package com.example.data.mapper

import com.example.core.Mapper
import com.example.data.R

interface WeatherIconMapper : Mapper<Int, String> {

    class Base : WeatherIconMapper {
        override fun map(source: String): Int {
            when(source) {
                "01d" -> return R.drawable.clear_day
                "01n" -> return R.drawable.clear_night
                "02d" -> return R.drawable.few_clouds_day
                "02n" -> return R.drawable.few_clouds_night
                "03d" -> return R.drawable.clouds
                "03n" -> return R.drawable.clouds
                "04d" -> return R.drawable.broken_clouds
                "04n" -> return R.drawable.broken_clouds
                "09d" -> return R.drawable.shower_rain
                "09n" -> return R.drawable.shower_rain
                "10d" -> return R.drawable.rain_day
                "10n" -> return R.drawable.rain_night
                "11d" -> return R.drawable.thunderstorm
                "11n" -> return R.drawable.thunderstorm
                "13d" -> return R.drawable.snow
                "13n" -> return R.drawable.snow
                "50d" -> return R.drawable.mist
                "50n" -> return R.drawable.mist
                else -> return 0
            }
        }
    }

}