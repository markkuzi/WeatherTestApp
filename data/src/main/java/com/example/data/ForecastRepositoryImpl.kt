package com.example.data

import com.example.core.HandleError
import com.example.core.ResponseResult
import com.example.data.cache.CacheForecastWeatherRequest
import com.example.data.mapper.ForecastWeatherMapper
import com.example.data.network.NetworkService
import com.example.forecast.domain.ForecastRepository
import com.example.forecast.domain.entity.ForecastWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class ForecastRepositoryImpl(
    private val service: NetworkService,
    private val cacheForecastWeather: CacheForecastWeatherRequest,
    private val handleError: HandleError<String>,
    private val mapper: ForecastWeatherMapper,
) : ForecastRepository {
    override suspend fun loadForecastWeather(city: String): ResponseResult = try {
        val forecastWeatherDto = service.getForecastWeather(city)
        cacheForecastWeather.saveCacheWeatherInfo(forecastWeatherDto)
        ResponseResult.Success()
    } catch (e: Exception) {
        ResponseResult.Error(handleError.handle(e))
    }

    override fun getForecastWeather(): Flow<ForecastWeather> =
        cacheForecastWeather.loadCacheWeatherInfo().transform {
            emit(mapper.map(it))
        }
}