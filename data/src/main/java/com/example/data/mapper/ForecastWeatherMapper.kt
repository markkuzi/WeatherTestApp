package com.example.data.mapper

import com.example.core.Mapper
import com.example.data.network.models.ForecastWeatherDto
import com.example.forecast.domain.entity.ForecastWeather
import com.example.forecast.domain.entity.WeatherList

interface ForecastWeatherMapper : Mapper<ForecastWeather, ForecastWeatherDto> {

    class Base : ForecastWeatherMapper {
        override fun map(source: ForecastWeatherDto): ForecastWeather = ForecastWeather(
            cityName = source.city?.cityName ?: "-",
            source.weatherList?.map { listDto ->
                WeatherList(
                    date = listDto.date ?: "-",
                    main = listDto.weather?.first()?.main ?: "-",
                    description = listDto.weather?.first()?.description ?: "-",
                    icon = listDto.weather?.first()?.icon ?: "-",
                    temp = listDto.main?.temp ?: "-",
                )
            } ?: emptyList()
        )
    }
}