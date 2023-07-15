package com.example.weathertestapp.di.modules

import com.example.core.HandleError
import com.example.core.ManageResources
import org.koin.dsl.module

val coreModule = module {

    factory<ManageResources> {
        ManageResources.Base(context = get())
    }

    factory<HandleError<String>> {
        HandleError.Base(manageResources = get())
    }

}