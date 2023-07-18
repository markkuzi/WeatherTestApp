package com.example.weathertestapp.di

import android.app.Application
import com.example.weathertestapp.di.modules.cacheModule
import com.example.weathertestapp.di.modules.coreModule
import com.example.weathertestapp.di.modules.detailsModule
import com.example.weathertestapp.di.modules.forecastModule
import com.example.weathertestapp.di.modules.mainModule
import com.example.weathertestapp.di.modules.mapperModule
import com.example.weathertestapp.di.modules.networkModule
import com.example.weathertestapp.di.modules.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                networkModule,
                cacheModule,
                mainModule,
                detailsModule,
                forecastModule,
                coreModule,
                mapperModule,
                storageModule,
            )
        }
    }
}