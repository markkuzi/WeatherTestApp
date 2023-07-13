package com.example.weathertestapp.di.modules

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

    factory { OkHttpClient.Builder()
        .addInterceptor(interceptor = get())
        .build() }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory<NetworkService>{ get<Retrofit>().create(NetworkService::class.java) }

}
