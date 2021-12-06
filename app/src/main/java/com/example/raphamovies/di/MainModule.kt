package com.example.raphamovies.di


import androidx.lifecycle.ViewModel
import com.example.raphamovies.viewmodel.DetailsViewModel
import com.example.raphamovies.viewmodel.HomeViewModel
import com.example.raphamovies.viewmodel.PersonViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonViewModel::class)
    fun bindPersonViewModel(viewModel: PersonViewModel): ViewModel

}