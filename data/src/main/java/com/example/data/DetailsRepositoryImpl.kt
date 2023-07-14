package com.example.data

import com.example.data.cache.CacheWeatherRequest
import com.example.data.cache.CacheWeatherRequestImpl
import com.example.details.domain.DetailsRepository
import com.example.details.domain.entity.DetailsWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class DetailsRepositoryImpl(
    private val cacheWeatherRequest: CacheWeatherRequest,
): DetailsRepository {

    override fun getDetailsWeather(): Flow<DetailsWeather> {
        return cacheWeatherRequest.loadCacheWeatherInfo().transform {
            emit(DetailsWeather(
                it.name ?: "-",
                it.weather?.first()?.main ?: "-",
                it.weather?.first()?.description ?: "-",
                it.main?.temp ?: "-",
                it.main?.feelsLike ?: "-",
                it.main?.tempMin ?: "-",
                it.main?.tempMax ?: "-",
                it.main?.humidity ?: "-",
                it.main?.pressure ?: "-",
                it.wind?.windSpeed ?: "-",
                it.wind?.deg ?: "-",
                it.wind?.gust ?: "-",
                it.systemWeatherInfo?.sunrise ?: "-",
                it.systemWeatherInfo?.sunset ?: "-",
            ))
        }
    }


}