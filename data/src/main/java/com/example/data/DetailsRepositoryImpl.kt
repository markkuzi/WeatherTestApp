package com.example.data

import com.example.core.HandleError
import com.example.core.ResponseResult
import com.example.data.cache.CacheMainWeatherRequest
import com.example.data.mapper.DetailsWeatherMapper
import com.example.data.network.NetworkService
import com.example.data.storage.SharedPrefsCityStorage
import com.example.details.domain.DetailsRepository
import com.example.details.domain.entity.DetailsWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class DetailsRepositoryImpl(
    private val cacheWeatherRequest: CacheMainWeatherRequest,
    private val mapper: DetailsWeatherMapper,
    private val service: NetworkService,
    private val handleError: HandleError<String>,
    private val storage: SharedPrefsCityStorage,
) : DetailsRepository {

    override fun getDetailsWeather(): Flow<DetailsWeather> =
        cacheWeatherRequest.loadCacheWeatherInfo()
            .transform {
                emit(mapper.map(it))
            }

    override suspend fun loadWeather(): ResponseResult = try {
        val mainWeatherDto = service.getMainWeather(storage.load())
        cacheWeatherRequest.saveCacheWeatherInfo(mainWeatherDto)
        ResponseResult.Success()
    } catch (e: Exception) {
        ResponseResult.Error(handleError.handle(e))
    }
}