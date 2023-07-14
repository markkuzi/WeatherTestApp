package com.example.data.network

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("cod") val cod: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("weather") val weather: List<Weather>?,
    @SerializedName("main") val main: Main?,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("sys") val systemWeatherInfo: SystemWeatherInfo?,
)

data class Weather(
    @SerializedName("main") val main: String?,
    @SerializedName("description") val description: String?,
)

data class Main(
    @SerializedName("temp") val temp: String?,
    @SerializedName("feels_like") val feelsLike: String?,
    @SerializedName("temp_min") val tempMin: String?,
    @SerializedName("temp_max") val tempMax: String?,
    @SerializedName("humidity") val humidity: String?,
    @SerializedName("pressure") val pressure: String?,
)

data class Wind(
    @SerializedName("speed") val windSpeed: String?,
    @SerializedName("deg") val deg: String?,
    @SerializedName("gust") val gust: String?,
)

data class SystemWeatherInfo(
    @SerializedName("sunrise") val sunrise: String?,
    @SerializedName("sunset") val sunset: String?,
)
