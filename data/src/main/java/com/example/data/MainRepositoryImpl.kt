package com.example.data

import com.example.data.cache.CacheWeatherRequest
import com.example.data.cache.CacheWeatherRequestImpl
import com.example.data.network.NetworkService
import com.example.main.domain.MainRepository
import com.example.main.domain.entity.MainWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class MainRepositoryImpl(
    private val service: NetworkService,
    private val cacheWeatherRequest: CacheWeatherRequest,
): MainRepository {
    override suspend fun loadWeather(city: String) {
        try {
            val mainWeatherDto = service.getMainWeather(city)
            cacheWeatherRequest.saveCacheWeatherInfo(mainWeatherDto)
        } catch (e: Exception) {
        }
    }

    override fun getMainWeather(): Flow<MainWeather> {
        return cacheWeatherRequest.loadCacheWeatherInfo().transform {
            emit(MainWeather(
                it.name ?: "-",
                it.main?.temp ?: "-",
                it.main?.humidity ?: "-",
                it.main?.pressure ?: "-",
                it.wind?.windSpeed ?: "-",
            ))
        }
    }
}