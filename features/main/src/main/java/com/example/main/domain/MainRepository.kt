package com.example.main.domain

import com.example.main.domain.entity.MainWeather

interface MainRepository {

    suspend fun getMainWeather(city: String): MainWeather

}