package com.example.data.network.models

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed") val windSpeed: String?,
    @SerializedName("deg") val deg: String?,
    @SerializedName("gust") val gust: String?,
)