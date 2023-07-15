package com.example.forecast.domain

import com.example.core.RequestCode
import com.example.forecast.domain.entity.ForecastWeather

interface ForecastRepository {

    fun loadForecastWeather(city: String) : RequestCode

    fun getForecastWeather(): ForecastWeather

}