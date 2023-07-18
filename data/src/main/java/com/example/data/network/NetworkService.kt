package com.example.data.network

import com.example.data.network.models.ForecastWeatherDto
import com.example.data.network.models.WeatherDto
import com.example.data.network.models.WeatherListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("data/2.5/weather")
    suspend fun getMainWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("lang") language: String = "en",
        @Query("appid") appId: String = "ec879d85094b4d1f93b19ce50af89f6a"
    ) : WeatherDto

    @GET("/data/2.5/forecast")
    suspend fun getForecastWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("lang") language: String = "en",
        @Query("appid") appId: String = "ec879d85094b4d1f93b19ce50af89f6a"
    ) : ForecastWeatherDto

}