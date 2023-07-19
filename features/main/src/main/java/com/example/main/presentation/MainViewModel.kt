package com.example.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ResponseResult
import com.example.core.ViewState
import com.example.main.domain.MainWeatherUseCase
import com.example.main.domain.entity.MainWeather
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainWeatherUseCase: MainWeatherUseCase,
) : ViewModel() {


    private var _mainWeather = MutableLiveData<MainWeather>()
    val mainWeather: LiveData<MainWeather>
        get() = _mainWeather


    private var _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    init {
        loadWeather(null)
        viewModelScope.launch {
            mainWeatherUseCase.getMainWeather().collect {
                _mainWeather.value = it
            }
        }
    }

    fun loadWeather(city: String?) {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading()
            if (city?.trim() != null)
                mainWeatherUseCase.saveCityName(city.trim())
            val responseResult = mainWeatherUseCase.loadWeather()
            when (responseResult) {
                is ResponseResult.Success -> _viewState.value = ViewState.Success()
                is ResponseResult.Error -> _viewState.value =
                    ViewState.Error(responseResult.message)
            }
        }
    }
}
