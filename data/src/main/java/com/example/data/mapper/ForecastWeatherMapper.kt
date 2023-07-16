package com.example.data.mapper

import com.example.core.DateWeatherMapper
import com.example.core.Mapper
import com.example.data.network.models.ForecastWeatherDto
import com.example.forecast.domain.entity.ForecastWeather
import com.example.forecast.domain.entity.WeatherList

interface ForecastWeatherMapper : Mapper<ForecastWeather, ForecastWeatherDto> {

    class Base(private val dateMapper: DateWeatherMapper) : ForecastWeatherMapper {
        override fun map(source: ForecastWeatherDto): ForecastWeather = ForecastWeather(
            cityName = source.city?.cityName ?: "-",
            source.weatherList?.map { listDto ->
                WeatherList(
                    date = dateMapper.map(listDto.date ?: "", source.city?.timeZone ?: ""),
                    main = listDto.weather?.first()?.main ?: "-",
                    description = listDto.weather?.first()?.description ?: "-",
                    icon = listDto.weather?.first()?.icon ?: "-",
                    temp = listDto.main?.temp ?: "-",
                )
            } ?: emptyList()
        )
    }
}