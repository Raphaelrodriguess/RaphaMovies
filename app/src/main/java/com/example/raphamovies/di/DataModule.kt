package com.example.raphamovies.di

import com.example.raphamovies.repository.HomeDataSource
import com.example.raphamovies.repository.HomeDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class DataModule {
    @Singleton
    @Binds
    abstract fun provideHomeDataSource(datasource: HomeDataSourceImpl): HomeDataSource
}