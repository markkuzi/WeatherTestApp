package com.example.data.network.models

import com.google.gson.annotations.SerializedName

data class SystemWeatherInfo(
    @SerializedName("sunrise") val sunrise: String?,
    @SerializedName("sunset") val sunset: String?,
)