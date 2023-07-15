package com.example.data.cache

import com.example.data.network.models.WeatherDto
import kotlinx.coroutines.flow.Flow

interface CacheWeatherRequest <T: Any> {

    suspend fun saveCacheWeatherInfo (weather: T)

    fun loadCacheWeatherInfo() : Flow<T>

}