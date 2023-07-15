package com.example.data.network.models

import com.google.gson.annotations.SerializedName


data class WeatherListDto(
    @SerializedName("dt") val date: String?,
    @SerializedName("main") val main: Main?,
    @SerializedName("weather") val weather: List<Weather>?,
)
