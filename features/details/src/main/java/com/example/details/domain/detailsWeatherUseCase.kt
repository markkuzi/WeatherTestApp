package com.example.details.domain

import com.example.core.ResponseResult
import com.example.details.domain.entity.DetailsWeather
import kotlinx.coroutines.flow.Flow

class detailsWeatherUseCase(
    private val repository: DetailsRepository
) {

    fun getDetailsWeather() : Flow<DetailsWeather> {
        return repository.getDetailsWeather()
    }

    suspend fun loadWeather(city: String) : ResponseResult {
        return repository.loadWeather(city)
    }

}