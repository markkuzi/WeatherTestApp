package com.example.forecast.domain

import com.example.core.RequestCode
import com.example.forecast.domain.entity.ForecastWeather

class ForecastWeatherUseCase(
    private val repository: ForecastRepository
) {

    fun loadForecastWeather(city: String) : RequestCode {
        return repository.loadForecastWeather(city)
    }

    fun getForecastWeather() : ForecastWeather {
        return repository.getForecastWeather()
    }

}