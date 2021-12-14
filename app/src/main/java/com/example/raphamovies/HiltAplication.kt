package com.example.raphamovies

import android.app.Application
import com.example.raphamovies.di.networkModule
import com.example.raphamovies.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class HiltAplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{

            androidContext(this@HiltAplication)

            modules(listOf(viewModelModule, networkModule))
        }
    }
}
