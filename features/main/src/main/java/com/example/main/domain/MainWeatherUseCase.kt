package com.example.main.domain

import com.example.core.ResponseResult
import com.example.main.domain.entity.MainWeather
import kotlinx.coroutines.flow.Flow

interface MainWeatherUseCase {

    suspend fun loadWeather(): ResponseResult

    fun getMainWeather(): Flow<MainWeather>

    suspend fun saveCityName(city: String)

    class Base(
        private val repository: MainRepository
    ): MainWeatherUseCase {
        override suspend fun loadWeather(): ResponseResult {
            return repository.loadWeather()
        }

        override fun getMainWeather(): Flow<MainWeather> {
            return repository.getMainWeather()
        }

        override suspend fun saveCityName(city: String) {
            repository.saveCityName(city)
        }
    }

}
