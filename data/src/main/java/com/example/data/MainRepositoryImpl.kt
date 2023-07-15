package com.example.data

import com.example.core.HandleError
import com.example.core.ResponseResult
import com.example.data.cache.CacheMainWeatherRequest
import com.example.data.network.NetworkService
import com.example.main.domain.MainRepository
import com.example.main.domain.entity.MainWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class MainRepositoryImpl(
    private val service: NetworkService,
    private val cacheWeatherRequest: CacheMainWeatherRequest,
    private val handleError: HandleError<String>,
) : MainRepository {
    override suspend fun loadWeather(city: String): ResponseResult = try {
        val mainWeatherDto = service.getMainWeather(city)
        cacheWeatherRequest.saveCacheWeatherInfo(mainWeatherDto)
        ResponseResult.Success()
    } catch (e: Exception) {
        ResponseResult.Error(handleError.handle(e))
    }

    override fun getMainWeather(): Flow<MainWeather> {
        return cacheWeatherRequest.loadCacheWeatherInfo().transform {
            emit(
                MainWeather(
                    it.name ?: "-",
                    it.main?.temp ?: "-",
                    it.main?.humidity ?: "-",
                    it.main?.pressure ?: "-",
                    it.wind?.windSpeed ?: "-",
                )
            )
        }
    }
}