package com.example.data

import com.example.data.cache.CacheMainWeatherRequest
import com.example.data.mapper.DetailsWeatherMapper
import com.example.details.domain.DetailsRepository
import com.example.details.domain.entity.DetailsWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class DetailsRepositoryImpl(
    private val cacheWeatherRequest: CacheMainWeatherRequest,
    private val mapper: DetailsWeatherMapper,
) : DetailsRepository {

    override fun getDetailsWeather(): Flow<DetailsWeather> =
        cacheWeatherRequest.loadCacheWeatherInfo()
            .transform {
                emit(mapper.map(it))
            }
}