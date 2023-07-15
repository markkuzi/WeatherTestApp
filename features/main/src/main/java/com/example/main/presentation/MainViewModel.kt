package com.example.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.ResponseResult
import com.example.core.ViewState
import com.example.main.domain.MainWeatherUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainWeatherUseCase: MainWeatherUseCase,
) : ViewModel() {


    val mainWeather = mainWeatherUseCase.getMainWeather().asLiveData()

    private var _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    init {
        if (mainWeather.value == null)
            loadWeather("sfasefas")
    }

    fun loadWeather(city: String) {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading()
            val responseResult = mainWeatherUseCase.loadWeather(city)
            when (responseResult) {
                is ResponseResult.Success -> _viewState.value = ViewState.Success()
                is ResponseResult.Error -> _viewState.value =
                    ViewState.Error(responseResult.message)
            }
        }
    }
}
