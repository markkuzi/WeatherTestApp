package com.example.data.mapper

import com.example.core.DateWeatherMapper
import com.example.core.Mapper
import com.example.data.network.models.WeatherDto
import com.example.details.domain.entity.DetailsWeather

interface DetailsWeatherMapper : Mapper<DetailsWeather, WeatherDto> {

    class Base(
        private val dateMapper: DateWeatherMapper,
        private val iconMapper: WeatherIconMapper,
        ) : DetailsWeatherMapper {
        override fun map(source: WeatherDto): DetailsWeather = DetailsWeather(
            date = dateMapper.map(source.date ?: "", source.timeZone ?: ""),
            name = source.name ?: "-",
            weatherMain = source.weather?.first()?.main ?: "-",
            weatherDescription = source.weather?.first()?.description ?: "-",
            temp = source.main?.temp ?: "-",
            feelsLike = source.main?.feelsLike ?: "-",
            tempMin = source.main?.tempMin ?: "-",
            tempMax = source.main?.tempMax ?: "-",
            humidity = source.main?.humidity ?: "-",
            pressure = source.main?.pressure ?: "-",
            windSpeed = source.wind?.windSpeed ?: "-",
            windDeg = source.wind?.deg ?: "-",
            windGust = source.wind?.gust ?: "-",
            sunrise = dateMapper.map(
                source.systemWeatherInfo?.sunrise ?: "",
                source.timeZone ?: "",
                1,
            ),
            sunset = dateMapper.map(
                source.systemWeatherInfo?.sunset ?: "",
                source.timeZone ?: "",
                1,
            ),
            icon = iconMapper.map(source.weather?.first()?.icon ?: ""),
        )
    }
}