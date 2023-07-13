package com.example.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.details.domain.GetDetailsWeatherUseCase
import com.example.details.domain.entity.DetailsWeather
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getDetailsWeatherUseCase: GetDetailsWeatherUseCase
) : ViewModel() {

    private var _detailsWeather = MutableLiveData<DetailsWeather>()
    val detailsWeather: LiveData<DetailsWeather>
        get() = _detailsWeather

    init {
        getDetailsWeather()
    }

    fun getDetailsWeather() {
        viewModelScope.launch {
            _detailsWeather.value = getDetailsWeatherUseCase.getDetailsWeather()
        }
    }

}