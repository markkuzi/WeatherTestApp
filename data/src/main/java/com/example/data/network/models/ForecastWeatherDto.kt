package com.example.data.network.models

import com.google.gson.annotations.SerializedName

data class ForecastWeatherDto(
    @SerializedName("cod") val cod: Int?,
    @SerializedName("list") val weatherList: List<WeatherListDto>?,
    @SerializedName("city") val city: WeatherCityDto?,
)