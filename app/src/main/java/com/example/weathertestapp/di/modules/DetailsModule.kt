package com.example.weathertestapp.di.modules

import com.example.data.DetailsRepositoryImpl
import com.example.details.domain.DetailsRepository
import com.example.details.domain.GetDetailsWeatherUseCase
import com.example.details.presentation.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {

    single<DetailsRepository> {
        DetailsRepositoryImpl(
            cacheWeatherRequest = get(),
            mapper = get(),
        )
    }

    factory<GetDetailsWeatherUseCase> {
        GetDetailsWeatherUseCase(
            repository = get(),
        )
    }

    viewModel {
        DetailsViewModel(
            getDetailsWeatherUseCase = get(),
        )
    }

}