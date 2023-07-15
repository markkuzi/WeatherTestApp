package com.example.forecast.domain

import com.example.core.RequestCode
import com.example.forecast.domain.entity.ForecastWeather
import kotlinx.coroutines.flow.Flow

class ForecastWeatherUseCase(
    private val repository: ForecastRepository
) {

    suspend fun loadForecastWeather(city: String) : RequestCode {
        return repository.loadForecastWeather(city)
    }

    fun getForecastWeather() : Flow<ForecastWeather> {
        return repository.getForecastWeather()
    }

}