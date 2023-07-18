package com.example.weathertestapp.di.modules

import com.example.data.ForecastRepositoryImpl
import com.example.forecast.domain.ForecastRepository
import com.example.forecast.domain.ForecastWeatherUseCase
import com.example.forecast.presentation.ForecastViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val forecastModule = module {

    single<ForecastRepository> {
        ForecastRepositoryImpl(
            service = get(),
            cacheForecastWeather = get(),
            handleError = get(),
            mapper = get(),
            storage = get(),
        )
    }

    factory<ForecastWeatherUseCase> {
        ForecastWeatherUseCase(
            repository = get(),
        )
    }

    viewModel {
        ForecastViewModel(
            forecastWeatherUseCase = get(),
        )
    }

}