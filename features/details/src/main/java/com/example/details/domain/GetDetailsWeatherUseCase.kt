package com.example.details.domain

import com.example.details.domain.entity.DetailsWeather

class GetDetailsWeatherUseCase(
    private val repository: DetailsRepository
) {

    suspend fun getDetailsWeather() : DetailsWeather {
        return repository.getDetailsWeather()
    }

}