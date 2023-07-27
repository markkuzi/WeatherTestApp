package com.example.forecast.domain

import com.example.core.ResponseResult
import com.example.forecast.domain.entity.ForecastWeather
import kotlinx.coroutines.flow.Flow

interface ForecastWeatherUseCase {

    suspend fun loadForecastWeather(getCache: Boolean): ResponseResult

    fun getForecastWeather(): Flow<ForecastWeather>

    class Base(
        private val repository: ForecastRepository,
    ) : ForecastWeatherUseCase {

        override suspend fun loadForecastWeather(getCache: Boolean): ResponseResult {
            return repository.loadForecastWeather(getCache)
        }

        override fun getForecastWeather(): Flow<ForecastWeather> {
            return repository.getForecastWeather()
        }
    }
}