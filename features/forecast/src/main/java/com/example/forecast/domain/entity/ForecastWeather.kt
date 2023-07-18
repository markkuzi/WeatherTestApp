package com.example.forecast.domain.entity

data class ForecastWeather(
    val cityName: String,
    val weatherList: List<WeatherList>
)