package com.example.main.domain

import com.example.core.RequestCode
import com.example.main.domain.entity.MainWeather
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun loadWeather(city: String): RequestCode
    fun getMainWeather(): Flow<MainWeather>

}