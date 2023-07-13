package com.example.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("data/2.5/weather")
    suspend fun getMainWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("lang") language: String = "en",
        @Query("appid") appId: String = "06c921750b9a82d8f5d1294e1586276f"
    ) : MainWeatherDto
}