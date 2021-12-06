package com.example.raphamovies.di

import com.example.raphamovies.MainActivity
import com.example.raphamovies.ui.HomeFragment
import com.example.raphamovies.ui.cast.PersonDetailsFragment
import com.example.raphamovies.ui.details.DetailsFragment
import dagger.Subcomponent

@Subcomponent(modules = [])
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: DetailsFragment)
    fun inject(fragment: PersonDetailsFragment)
}