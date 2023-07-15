package com.example.data

import com.example.core.HandleError
import com.example.core.ResponseResult
import com.example.data.cache.CacheForecastWeatherRequest
import com.example.data.network.NetworkService
import com.example.forecast.domain.ForecastRepository
import com.example.forecast.domain.entity.ForecastWeather
import com.example.forecast.domain.entity.WeatherList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class ForecastRepositoryImpl(
    private val service: NetworkService,
    private val cacheForecastWeather: CacheForecastWeatherRequest,
    private val handleError: HandleError<String>,
) : ForecastRepository {
    override suspend fun loadForecastWeather(city: String): ResponseResult = try {
        val forecastWeatherDto = service.getForecastWeather(city)
        cacheForecastWeather.saveCacheWeatherInfo(forecastWeatherDto)
        ResponseResult.Success()
    } catch (e: Exception) {
        ResponseResult.Error(handleError.handle(e))
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