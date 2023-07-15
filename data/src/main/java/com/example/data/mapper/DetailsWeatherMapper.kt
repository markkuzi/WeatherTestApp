package com.example.data.mapper

import com.example.core.Mapper
import com.example.data.network.models.WeatherDto
import com.example.details.domain.entity.DetailsWeather

interface DetailsWeatherMapper : Mapper<DetailsWeather, WeatherDto> {

    class Base : DetailsWeatherMapper {
        override fun map(source: WeatherDto): DetailsWeather = DetailsWeather(
            source.name ?: "-",
            source.weather?.first()?.main ?: "-",
            source.weather?.first()?.description ?: "-",
            source.main?.temp ?: "-",
            source.main?.feelsLike ?: "-",
            source.main?.tempMin ?: "-",
            source.main?.tempMax ?: "-",
            source.main?.humidity ?: "-",
            source.main?.pressure ?: "-",
            source.wind?.windSpeed ?: "-",
            source.wind?.deg ?: "-",
            source.wind?.gust ?: "-",
            source.systemWeatherInfo?.sunrise ?: "-",
            source.systemWeatherInfo?.sunset ?: "-",
        )
    }
}