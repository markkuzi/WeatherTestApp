package com.example.details.domain

import com.example.details.domain.entity.DetailsWeather
import kotlinx.coroutines.flow.Flow

class GetDetailsWeatherUseCase(
    private val repository: DetailsRepository
) {

    fun getDetailsWeather() : Flow<DetailsWeather> {
        return repository.getDetailsWeather()
    }

}