package com.example.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.details.domain.GetDetailsWeatherUseCase
import com.example.details.domain.entity.DetailsWeather
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getDetailsWeatherUseCase: GetDetailsWeatherUseCase
) : ViewModel() {

    val detailsWeather = getDetailsWeatherUseCase.getDetailsWeather().asLiveData()


}