package com.example.details.domain

import com.example.core.ResponseResult
import com.example.details.domain.entity.DetailsWeather
import kotlinx.coroutines.flow.Flow


interface DetailsWeatherUseCase {

    fun getDetailsWeather(): Flow<DetailsWeather>

    suspend fun loadWeather(): ResponseResult

    class Base(
        private val repository: DetailsRepository,
    ): DetailsWeatherUseCase {
        override fun getDetailsWeather(): Flow<DetailsWeather> {
            return repository.getDetailsWeather()
        }

        override suspend fun loadWeather(): ResponseResult {
            return repository.loadWeather()
        }
    }
}
