package com.example.weathertestapp.di.modules

import com.example.core.DateWeatherMapper
import com.example.data.cache.CacheForecastWeatherRequest
import com.example.data.cache.CacheMainWeatherRequest
import com.example.data.mapper.DetailsWeatherMapper
import com.example.data.mapper.ForecastWeatherMapper
import com.example.data.mapper.MainWeatherMapper
import com.example.data.mapper.WeatherIconMapper
import com.example.data.mapper.WeatherTempMapper
import com.example.data.network.NetworkService
import com.example.data.storage.SharedPrefsCityStorage
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
        CacheMainWeatherRequest.Base()
    }

    single<CacheForecastWeatherRequest> {
        CacheForecastWeatherRequest.Base()
    }

}

val storageModule = module {

    single<SharedPrefsCityStorage> {
        SharedPrefsCityStorage.Base(context = get())
    }
}

val mapperModule = module {

    factory<MainWeatherMapper> {
        MainWeatherMapper.Base(
            dateMapper = get(),
            iconMapper = get(),
            tempMapper = get(),
        )
    }

    factory<DetailsWeatherMapper> {
        DetailsWeatherMapper.Base(
            dateMapper = get(),
            iconMapper = get(),
            tempMapper = get(),
        )
    }

    factory<ForecastWeatherMapper> {
        ForecastWeatherMapper.Base(
            dateMapper = get(),
            iconMapper = get(),
            tempMapper = get(),
        )
    }

    factory<DateWeatherMapper> {
        DateWeatherMapper.Base()
    }

    factory<WeatherIconMapper> {
        WeatherIconMapper.Base()
    }

    factory<WeatherTempMapper> {
        WeatherTempMapper.Base()
    }

}