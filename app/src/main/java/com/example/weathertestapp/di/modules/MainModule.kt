package com.example.weathertestapp.di.modules

import com.example.data.MainRepositoryImpl
import com.example.main.domain.MainRepository
import com.example.main.domain.MainWeatherUseCase
import com.example.main.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single<MainRepository> {
        MainRepositoryImpl(
            service = get(),
            cacheWeatherRequest = get(),
            handleError = get(),
            mapper = get(),
            storage = get(),
        )
    }

    factory<MainWeatherUseCase> {
        MainWeatherUseCase(
            repository = get(),
        )
    }

    viewModel {
        MainViewModel(
            mainWeatherUseCase = get(),
        )
    }

}