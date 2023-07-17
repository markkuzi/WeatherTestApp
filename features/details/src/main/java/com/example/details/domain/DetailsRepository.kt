package com.example.details.domain

import com.example.core.ResponseResult
import com.example.details.domain.entity.DetailsWeather
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    fun getDetailsWeather(): Flow<DetailsWeather>
    suspend fun loadWeather(city: String): ResponseResult

}