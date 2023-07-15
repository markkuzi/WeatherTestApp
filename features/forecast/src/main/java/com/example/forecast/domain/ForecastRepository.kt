package com.example.forecast.domain

import com.example.core.RequestCode
import com.example.forecast.domain.entity.ForecastWeather
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {

    suspend fun loadForecastWeather(city: String) : RequestCode

    fun getForecastWeather(): Flow<ForecastWeather>

}