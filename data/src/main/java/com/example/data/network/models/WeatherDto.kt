package com.example.data.network.models

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("dt") val date: String?,
    @SerializedName("timezone") val timeZone: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("weather") val weather: List<Weather>?,
    @SerializedName("main") val main: Main?,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("sys") val systemWeatherInfo: SystemWeatherInfo?,
)

