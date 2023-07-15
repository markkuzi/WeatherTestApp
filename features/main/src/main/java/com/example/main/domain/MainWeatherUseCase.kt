package com.example.main.domain

import com.example.core.RequestCode
import com.example.main.domain.entity.MainWeather
import kotlinx.coroutines.flow.Flow

class MainWeatherUseCase(
    private val repository: MainRepository,
) {

    suspend fun loadWeather(city: String): RequestCode {
        return repository.loadWeather(city)
    }

    fun getMainWeather(): Flow<MainWeather> {
        return repository.getMainWeather()
    }

}