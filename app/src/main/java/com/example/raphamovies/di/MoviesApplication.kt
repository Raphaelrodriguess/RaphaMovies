package com.example.raphamovies.di

import android.app.Application
import com.example.raphamovies.di.ApplicationComponent

class MoviesApplication: Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(this)
    }
}