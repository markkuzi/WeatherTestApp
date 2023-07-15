package com.example.data.cache

import com.example.data.network.models.Main
import com.example.data.network.models.SystemWeatherInfo
import com.example.data.network.models.Weather
import com.example.data.network.models.WeatherDto
import com.example.data.network.models.Wind
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface CacheMainWeatherRequest : CacheWeatherRequest<WeatherDto> {

    class Base : CacheMainWeatherRequest {

        private val cacheWeather = MutableStateFlow(
            WeatherDto(
                0,
                "",
                listOf(Weather("", "", "")),
                Main("", "", "", "", "", ""),
                Wind("", "", ""),
                SystemWeatherInfo("", "")
            )
        )

        override suspend fun saveCacheWeatherInfo(weather: WeatherDto) {
            cacheWeather.value = weather
        }

        override fun loadCacheWeatherInfo(): Flow<WeatherDto> {
            return cacheWeather.asStateFlow()
        }
    }
}