package com.example.data.cache

import com.example.data.network.Main
import com.example.data.network.SystemWeatherInfo
import com.example.data.network.Weather
import com.example.data.network.WeatherDto
import com.example.data.network.Wind
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CacheWeatherRequestImpl: CacheWeatherRequest {

    private val cacheWeather = MutableStateFlow(
        WeatherDto(
            0,
            "",
            listOf(Weather("", "")),
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