package com.example.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.main.domain.MainWeatherUseCase
import com.example.main.domain.entity.MainWeather
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainWeatherUseCase: MainWeatherUseCase
): ViewModel() {


    val mainWeather = mainWeatherUseCase.getMainWeather().asLiveData()

    fun loadWeather(city: String) {
        viewModelScope.launch {
            mainWeatherUseCase.loadWeather(city)
        }
    }

    fun init(firstRun: Boolean) {
        loadWeather("Санкт Петербург")
    }
}