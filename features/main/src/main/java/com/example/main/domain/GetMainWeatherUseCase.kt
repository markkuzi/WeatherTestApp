package com.example.main.domain

import com.example.main.domain.entity.MainWeather

class GetMainWeatherUseCase(
    private val repository: MainRepository
) {

    suspend fun getMainWeather(city: String): MainWeather {
        return repository.getMainWeather(city)
    }

}