package com.example.forecast.domain.entity

data class WeatherList(
    val date: String,
    val temp: String,
    val main: String,
    val description: String,
    val icon: String,
)