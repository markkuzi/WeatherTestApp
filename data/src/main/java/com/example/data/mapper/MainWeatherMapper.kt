package com.example.data.mapper

import com.example.core.Mapper
import com.example.data.network.models.WeatherDto
import com.example.main.domain.entity.MainWeather

interface MainWeatherMapper : Mapper<MainWeather, WeatherDto> {

    class Base : MainWeatherMapper {
        override fun map(source: WeatherDto): MainWeather = MainWeather(
            source.name ?: "-",
            source.main?.temp ?: "-",
            source.main?.humidity ?: "-",
            source.main?.pressure ?: "-",
            source.wind?.windSpeed ?: "-",
        )
    }
}