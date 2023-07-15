package com.example.data.cache

import com.example.data.network.models.ForecastWeatherDto
import kotlinx.coroutines.flow.Flow

interface CacheForecastWeatherRequest:CacheWeatherRequest<ForecastWeatherDto>