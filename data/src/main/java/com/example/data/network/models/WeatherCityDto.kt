package com.example.data.network.models

import com.google.gson.annotations.SerializedName

data class WeatherCityDto(
    @SerializedName("name") val cityName: String?,
)
