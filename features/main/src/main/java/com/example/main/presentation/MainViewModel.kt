package com.example.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.main.domain.MainWeatherUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainWeatherUseCase: MainWeatherUseCase,
) : ViewModel() {


    val mainWeather = mainWeatherUseCase.getMainWeather().asLiveData()

    init {
        if (mainWeather.value == null)
            loadWeather("moscow")
    }

    fun loadWeather(city: String) {
        viewModelScope.launch {
            val code = mainWeatherUseCase.loadWeather(city)
        }
    }
}
