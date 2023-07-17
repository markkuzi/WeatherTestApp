package com.example.weathertestapp.di.modules

import com.example.data.DetailsRepositoryImpl
import com.example.details.domain.DetailsRepository
import com.example.details.domain.detailsWeatherUseCase
import com.example.details.presentation.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {

    single<DetailsRepository> {
        DetailsRepositoryImpl(
            cacheWeatherRequest = get(),
            mapper = get(),
            service = get(),
            handleError = get(),
        )
    }

    factory<detailsWeatherUseCase> {
        detailsWeatherUseCase(
            repository = get(),
        )
    }

    viewModel {
        DetailsViewModel(
            detailsWeatherUseCase = get(),
        )
    }

}