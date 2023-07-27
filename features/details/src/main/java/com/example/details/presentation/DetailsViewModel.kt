package com.example.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.ResponseResult
import com.example.core.ViewState
import com.example.details.domain.DetailsWeatherUseCase
import com.example.details.domain.entity.DetailsWeather
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val detailsWeatherUseCase: DetailsWeatherUseCase,
) : ViewModel() {

    //val detailsWeather = detailsWeatherUseCase.getDetailsWeather().asLiveData()

    private var _detailsWeather = MutableLiveData<DetailsWeather>()
    val detailsWeather: LiveData<DetailsWeather>
        get() = _detailsWeather

    private var _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    init {
        _viewState.value = ViewState.Success()
        viewModelScope.launch {
            detailsWeatherUseCase.getDetailsWeather().collect{
                _detailsWeather.value = it
            }
        }
    }

    fun loadWeather() {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading()
            val responseResult = detailsWeatherUseCase.loadWeather()
            when (responseResult) {
                is ResponseResult.Success -> {
                    _viewState.value = ViewState.Success()
                }
                is ResponseResult.Error -> _viewState.value =
                    ViewState.Error(responseResult.message)
            }
        }
    }
}