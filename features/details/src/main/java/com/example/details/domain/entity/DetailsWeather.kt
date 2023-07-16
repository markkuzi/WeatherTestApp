package com.example.details.domain.entity

data class DetailsWeather(
    val date: String,
    val name: String,
    val weatherMain: String,
    val weatherDescription: String,
    val temp: String,
    val feelsLike: String,
    val tempMin: String,
    val tempMax: String,
    val humidity: String,
    val pressure: String,
    val windSpeed: String,
    val windDeg: String,
    val windGust: String,
    val sunrise: String,
    val sunset: String,
)
