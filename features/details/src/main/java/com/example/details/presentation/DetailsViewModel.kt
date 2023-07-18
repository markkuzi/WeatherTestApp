package com.example.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.ResponseResult
import com.example.core.ViewState
import com.example.details.domain.detailsWeatherUseCase
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val detailsWeatherUseCase: detailsWeatherUseCase,
) : ViewModel() {

    val detailsWeather = detailsWeatherUseCase.getDetailsWeather().asLiveData()

    private var _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    init {
        _viewState.value = ViewState.Success()
    }

    fun loadWeather() {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading()
            val responseResult = detailsWeatherUseCase.loadWeather()
            when (responseResult) {
                is ResponseResult.Success -> _viewState.value = ViewState.Success()
                is ResponseResult.Error -> _viewState.value =
                    ViewState.Error(responseResult.message)
            }
        }
    }
}