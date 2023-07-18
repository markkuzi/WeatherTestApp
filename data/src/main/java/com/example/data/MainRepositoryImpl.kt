package com.example.data

import com.example.core.HandleError
import com.example.core.ResponseResult
import com.example.data.cache.CacheMainWeatherRequest
import com.example.data.mapper.MainWeatherMapper
import com.example.data.network.NetworkService
import com.example.data.storage.SharedPrefsCityStorage
import com.example.main.domain.MainRepository
import com.example.main.domain.entity.MainWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class MainRepositoryImpl(
    private val service: NetworkService,
    private val cacheWeatherRequest: CacheMainWeatherRequest,
    private val handleError: HandleError<String>,
    private val mapper: MainWeatherMapper,
    private val storage: SharedPrefsCityStorage,
) : MainRepository {
    override suspend fun loadWeather(): ResponseResult = try {
        val mainWeatherDto = service.getMainWeather(storage.load())
        cacheWeatherRequest.saveCacheWeatherInfo(mainWeatherDto)
        ResponseResult.Success()
    } catch (e: Exception) {
        ResponseResult.Error(handleError.handle(e))
    }

    override fun getMainWeather(): Flow<MainWeather> = cacheWeatherRequest.loadCacheWeatherInfo()
        .transform {
            emit(mapper.map(it))
        }

    override suspend fun saveCityName(city: String) {
        storage.save(city)
    }
}
