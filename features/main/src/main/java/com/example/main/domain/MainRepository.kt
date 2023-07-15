package com.example.main.domain

import com.example.core.ResponseResult
import com.example.main.domain.entity.MainWeather
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun loadWeather(city: String): ResponseResult
    fun getMainWeather(): Flow<MainWeather>

}