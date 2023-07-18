package com.example.data

import com.example.core.HandleError
import com.example.core.ResponseResult
import com.example.data.cache.CacheForecastWeatherRequest
import com.example.data.mapper.ForecastWeatherMapper
import com.example.data.network.NetworkService
import com.example.data.storage.SharedPrefsCityStorage
import com.example.forecast.domain.ForecastRepository
import com.example.forecast.domain.entity.ForecastWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class ForecastRepositoryImpl(
    private val service: NetworkService,
    private val cacheForecastWeather: CacheForecastWeatherRequest,
    private val handleError: HandleError<String>,
    private val mapper: ForecastWeatherMapper,
    private val storage: SharedPrefsCityStorage,
) : ForecastRepository {

    private var cityName: String? = null

    override suspend fun loadForecastWeather(getCache: Boolean): ResponseResult {
        try {
            val cityNameStorage = storage.load()
            if (cityName == cityNameStorage && getCache) return ResponseResult.Success()
            cityName = cityNameStorage
            val forecastWeatherDto = service.getForecastWeather(cityNameStorage)
            cacheForecastWeather.saveCacheWeatherInfo(forecastWeatherDto)
            return ResponseResult.Success()
        } catch (e: Exception) {
            return ResponseResult.Error(handleError.handle(e))
        }
    }

    override fun getForecastWeather(): Flow<ForecastWeather> =
        cacheForecastWeather.loadCacheWeatherInfo().transform {
            emit(mapper.map(it))
        }
}