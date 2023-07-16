package com.example.data.mapper

import com.example.core.DateWeatherMapper
import com.example.core.Mapper
import com.example.data.network.models.WeatherDto
import com.example.main.domain.entity.MainWeather

interface MainWeatherMapper : Mapper<MainWeather, WeatherDto> {

    class Base(
        private val dateMapper: DateWeatherMapper,
        private val iconMapper: WeatherIconMapper,
    ) : MainWeatherMapper {
        override fun map(source: WeatherDto): MainWeather = MainWeather(
            date = dateMapper.map(source.date ?: "", source.timeZone ?: ""),
            name = source.name ?: "-",
            temp = source.main?.temp ?: "-",
            humidity = source.main?.humidity ?: "-",
            pressure = source.main?.pressure ?: "-",
            windSpeed = source.wind?.windSpeed ?: "-",
            icon = iconMapper.map(source.weather?.first()?.icon ?: "")
        )
    }
}