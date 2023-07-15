package com.example.data.cache

import com.example.data.network.models.WeatherDto

interface CacheMainWeatherRequest: CacheWeatherRequest<WeatherDto> {
}