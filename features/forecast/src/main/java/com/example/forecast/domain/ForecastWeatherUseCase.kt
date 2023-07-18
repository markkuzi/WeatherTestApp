package com.example.forecast.domain

import com.example.core.ResponseResult
import com.example.forecast.domain.entity.ForecastWeather
import kotlinx.coroutines.flow.Flow

class ForecastWeatherUseCase(
    private val repository: ForecastRepository,
) {

    suspend fun loadForecastWeather(getCache: Boolean): ResponseResult {
        return repository.loadForecastWeather(getCache)
    }

    fun getForecastWeather(): Flow<ForecastWeather> {
        return repository.getForecastWeather()
    }

}