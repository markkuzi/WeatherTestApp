package com.example.weathertestapp.di.modules

import com.example.data.cache.CacheForecastWeather
import com.example.data.cache.CacheForecastWeatherRequest
import com.example.data.cache.CacheMainWeather
import com.example.data.cache.CacheMainWeatherRequest
import com.example.data.mapper.DetailsWeatherMapper
import com.example.data.mapper.ForecastWeatherMapper
import com.example.data.mapper.MainWeatherMapper
import com.example.data.network.NetworkService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    factory<Interceptor> {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(interceptor = get())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory<NetworkService> { get<Retrofit>().create(NetworkService::class.java) }

}

val cacheModule = module {

    single<CacheMainWeatherRequest> {
        CacheMainWeather()
    }

    single<CacheForecastWeatherRequest> {
        CacheForecastWeather()
    }

}

val mapperModule = module {

    factory<MainWeatherMapper> {
        MainWeatherMapper.Base()
    }

    factory<DetailsWeatherMapper> {
        DetailsWeatherMapper.Base()
    }

    factory<ForecastWeatherMapper> {
        ForecastWeatherMapper.Base()
    }

}