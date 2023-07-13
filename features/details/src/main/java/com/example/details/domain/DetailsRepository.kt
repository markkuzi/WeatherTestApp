package com.example.details.domain

import com.example.details.domain.entity.DetailsWeather

interface DetailsRepository {

    suspend fun getDetailsWeather(): DetailsWeather

}