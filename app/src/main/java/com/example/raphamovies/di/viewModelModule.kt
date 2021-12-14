package com.example.raphamovies.di


import com.example.raphamovies.repository.HomeDataSource
import com.example.raphamovies.repository.HomeDataSourceImpl
import com.example.raphamovies.viewmodel.DetailsViewModel
import com.example.raphamovies.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    factory<HomeDataSource> { HomeDataSourceImpl(get()) }


    viewModel {
        HomeViewModel(
        get()
        )
    }

    viewModel {
        DetailsViewModel(
            get()
        )
    }

}









