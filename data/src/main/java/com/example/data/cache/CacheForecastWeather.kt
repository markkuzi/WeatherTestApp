package com.example.data.cache

import com.example.data.network.models.ForecastWeatherDto
import com.example.data.network.models.Main
import com.example.data.network.models.Weather
import com.example.data.network.models.WeatherCityDto
import com.example.data.network.models.WeatherListDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CacheForecastWeather : CacheWeatherRequest<ForecastWeatherDto> {

    private val cacheForecastWeather = MutableStateFlow(
        ForecastWeatherDto(
            0,
            listOf(
                WeatherListDto(
                    "",
                    Main("", "", "", "", "", ""),
                    listOf(Weather("", "", ""))
                )
            ),
            WeatherCityDto(""),
        )
    )

    override suspend fun saveCacheWeatherInfo(weather: ForecastWeatherDto) {
        cacheForecastWeather.value = weather
    }

    override fun loadCacheWeatherInfo(): Flow<ForecastWeatherDto> {
        return cacheForecastWeather.asStateFlow()
    }
}