package com.example.data

import com.example.core.RequestCode
import com.example.data.cache.CacheWeatherRequest
import com.example.data.network.NetworkService
import com.example.main.domain.MainRepository
import com.example.main.domain.entity.MainWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import retrofit2.HttpException
import java.net.UnknownHostException

class MainRepositoryImpl(
    private val service: NetworkService,
    private val cacheWeatherRequest: CacheWeatherRequest,
) : MainRepository {
    override suspend fun loadWeather(city: String): RequestCode {
        try {
            val mainWeatherDto = service.getMainWeather(city)
            cacheWeatherRequest.saveCacheWeatherInfo(mainWeatherDto)
            return RequestCode.SUCCESS
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    return when (e.code()) {
                        RequestCode.BAD_REQUEST.code -> {
                            RequestCode.BAD_REQUEST
                        }

                        RequestCode.UNAUTHORIZED.code -> {
                            RequestCode.UNAUTHORIZED
                        }

                        RequestCode.NOT_FOUND.code -> {
                            RequestCode.NOT_FOUND
                        }

                        RequestCode.TOO_MANY_REQUEST.code -> {
                            RequestCode.TOO_MANY_REQUEST
                        }

                        else -> {
                            RequestCode.ERROR
                        }
                    }
                }

                is UnknownHostException -> return RequestCode.NO_INTERNET_ERROR
                else -> return RequestCode.ERROR
            }
        }
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