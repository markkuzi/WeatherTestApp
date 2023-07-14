package com.example.data.cache

import com.example.data.network.WeatherDto
import kotlinx.coroutines.flow.Flow

interface CacheWeatherRequest {

    suspend fun saveCacheWeatherInfo(weather: WeatherDto)

    fun loadCacheWeatherInfo() : Flow<WeatherDto>

}