package com.example.main.domain

import com.example.core.ResponseResult
import com.example.main.domain.entity.MainWeather
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun loadWeather(): ResponseResult
    fun getMainWeather(): Flow<MainWeather>

    suspend fun saveCityName(city: String)

}