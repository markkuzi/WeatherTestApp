package com.example.data.network.models

import com.google.gson.annotations.SerializedName

data class WeatherCityDto(
    @SerializedName("timezone") val timeZone: String?,
    @SerializedName("name") val cityName: String?,
)
