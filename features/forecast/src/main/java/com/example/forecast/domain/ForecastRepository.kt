package com.example.forecast.domain

import com.example.core.ResponseResult
import com.example.forecast.domain.entity.ForecastWeather
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {

    suspend fun loadForecastWeather(city: String, getCache: Boolean) : ResponseResult

    fun getForecastWeather(): Flow<ForecastWeather>

}