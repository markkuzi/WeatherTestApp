package com.example.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.RequestCode
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
            loadWeather("moscow")
    }

    fun loadWeather(city: String) {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading()
            val code = mainWeatherUseCase.loadWeather(city)
            when(code) {
                RequestCode.SUCCESS -> _viewState.value = ViewState.Success()
                RequestCode.BAD_REQUEST -> _viewState.value = ViewState.Error(RequestCode.BAD_REQUEST.name)
                RequestCode.UNAUTHORIZED -> _viewState.value = ViewState.Error(RequestCode.UNAUTHORIZED.name)
                RequestCode.NOT_FOUND -> _viewState.value = ViewState.Error(RequestCode.NOT_FOUND.name)
                RequestCode.TOO_MANY_REQUEST -> _viewState.value = ViewState.Error(RequestCode.TOO_MANY_REQUEST.name)
                RequestCode.NO_INTERNET_ERROR -> _viewState.value = ViewState.Error(RequestCode.NO_INTERNET_ERROR.name)
                RequestCode.ERROR -> _viewState.value = ViewState.Error(RequestCode.ERROR.name)
            }
        }
    }
}
