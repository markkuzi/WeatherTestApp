package com.example.forecast.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.ResponseResult
import com.example.core.ViewState
import com.example.forecast.domain.ForecastWeatherUseCase
import kotlinx.coroutines.launch

class ForecastViewModel(
    private val forecastWeatherUseCase: ForecastWeatherUseCase,
) : ViewModel() {

    val forecastWeather = forecastWeatherUseCase.getForecastWeather().asLiveData()

    private var _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    fun loadForecastWeather(city: String) {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading()
            val responseResult = forecastWeatherUseCase.loadForecastWeather(city)
            when (responseResult) {
                is ResponseResult.Success -> _viewState.value = ViewState.Success()
                is ResponseResult.Error -> _viewState.value =
                    ViewState.Error(responseResult.message)
            }
        }
    }
}

