package com.example.data

import com.example.core.RequestCode
import com.example.data.cache.CacheForecastWeather
import com.example.data.cache.CacheWeatherRequest
import com.example.data.network.NetworkService
import com.example.data.network.models.ForecastWeatherDto
import com.example.forecast.domain.ForecastRepository
import com.example.forecast.domain.entity.ForecastWeather
import com.example.forecast.domain.entity.WeatherList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import retrofit2.HttpException
import java.net.UnknownHostException

class ForecastRepositoryImpl(
    private val service: NetworkService,
    private val cacheForecastWeather: CacheWeatherRequest<ForecastWeatherDto>,
) : ForecastRepository {
    override suspend fun loadForecastWeather(city: String): RequestCode {
        try {
            val forecastWeatherDto = service.getForecastWeather(city)
            cacheForecastWeather.saveCacheWeatherInfo(forecastWeatherDto)
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

    override fun getForecastWeather(): Flow<ForecastWeather> {
        return cacheForecastWeather.loadCacheWeatherInfo().transform {
            emit(
                ForecastWeather(
                cityName = it.city?.cityName ?: "-",
                    it.weatherList?.map { listDto ->
                        WeatherList(
                            date = listDto.date ?: "-",
                            main = listDto.weather?.first()?.main ?: "-",
                            description = listDto.weather?.first()?.description ?: "-",
                            icon = listDto.weather?.first()?.icon ?: "-",
                            temp = listDto.main?.temp ?: "-",
                        )
                    } ?: emptyList()
            )
            )
        }
    }
}