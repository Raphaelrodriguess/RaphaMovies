package com.example.raphamovies


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.raphamovies.di.MainComponent
import com.example.raphamovies.di.MoviesApplication

class MainActivity : AppCompatActivity() {

    lateinit var mainComponent: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (applicationContext as MoviesApplication).appComponent.mainComponent().create()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}



