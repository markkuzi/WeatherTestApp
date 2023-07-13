package com.example.data.network

import com.google.gson.annotations.SerializedName

data class MainWeatherDto(
    @SerializedName("name") val name: String,
    @SerializedName("main") val main: Main,
    @SerializedName("wind") val wind: Wind,
)

data class Main(
    @SerializedName("temp") val temp: String,
    @SerializedName("humidity") val humidity: String,
    @SerializedName("pressure") val pressure: String,
)

data class Wind(
    @SerializedName("speed") val windSpeed: String,
)
