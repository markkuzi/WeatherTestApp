package com.example.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main.domain.GetMainWeatherUseCase
import com.example.main.domain.entity.MainWeather
import kotlinx.coroutines.launch

class MainViewModel(
    private val getMainWeatherUseCase: GetMainWeatherUseCase
): ViewModel() {

    private var _mainWeather = MutableLiveData<MainWeather>()
    val mainWeather: LiveData<MainWeather>
        get() = _mainWeather

    init {
        getMainWeather("Санкт Петербург")
    }

    fun getMainWeather(city: String) {
        viewModelScope.launch {
            _mainWeather.value = getMainWeatherUseCase.getMainWeather(city)
        }
    }

}