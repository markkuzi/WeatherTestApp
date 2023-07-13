package com.example.data.network

import com.google.gson.annotations.SerializedName

data class MainWeatherDto(
    @SerializedName("name") val name: String,
    @SerializedName("temp") val temp: String,
    @SerializedName("humidity") val humidity: String,
    @SerializedName("pressure") val pressure: String,
    @SerializedName("speed") val windSpeed: String,
)
