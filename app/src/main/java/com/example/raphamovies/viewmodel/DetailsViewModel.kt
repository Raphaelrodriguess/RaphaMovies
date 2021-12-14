package com.example.raphamovies.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raphamovies.appConstants
import com.example.raphamovies.domainmappers.toDetails
import com.example.raphamovies.domainmodel.Details
import com.example.raphamovies.network.NetworkResponse
import com.example.raphamovies.network.TmdbApi
import com.example.raphamovies.network.model.dto.VideoDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject


class DetailsViewModel (val context: Context) : ViewModel(), KoinComponent {

    private val tmdbApi by inject<TmdbApi>()

    private val _movie: MutableLiveData<Details> = MutableLiveData()
    val movie: LiveData<Details> = _movie

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _video: MutableLiveData<List<VideoDTO>> = MutableLiveData()
    val video: LiveData<List<VideoDTO>> = _video

    fun getDetails(id: Int) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            val response = tmdbApi.getDetails(id, appConstants.VIDEOS_AND_CASTS)
            when (response) {
                is NetworkResponse.Success -> {
                    val details = response.body.toDetails()
                    _movie.value = details
                    val video = response.body.video.results
                    _video.value = video

                }
                is NetworkResponse.ApiError -> Log.d(
                    "detailsviewmodel",
                    "ApiError ${response.body.message}"
                )
                is NetworkResponse.NetworkError -> Log.d("detailsviewmodel", "NetworkError")
                is NetworkResponse.UnknownError -> Log.d("detailsviewmodel", "UnknownError")
            }
        }
    }
}


