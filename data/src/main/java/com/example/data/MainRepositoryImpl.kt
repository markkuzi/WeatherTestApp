package com.example.data

import com.example.data.network.NetworkService
import com.example.main.domain.MainRepository
import com.example.main.domain.entity.MainWeather

class MainRepositoryImpl(
    private val service: NetworkService
): MainRepository {
    override suspend fun getMainWeather(city: String): MainWeather {
        try {
            val mainWeatherDto = service.getMainWeather(city)
            return MainWeather(
                mainWeatherDto.name,
                mainWeatherDto.temp,
                mainWeatherDto.humidity,
                mainWeatherDto.pressure,
                mainWeatherDto.windSpeed,
            )
        } catch (e: Exception) {
            return MainWeather(
                "",
                "",
                "",
                "",
                ""
            )
        }
    }
}