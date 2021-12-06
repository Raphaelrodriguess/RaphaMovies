package com.example.raphamovies.viewmodel

import android.content.Context
import android.telecom.Call
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raphamovies.appConstants
import com.example.raphamovies.di.MainDispatcher
import com.example.raphamovies.domainmappers.toDetails
import com.example.raphamovies.domainmodel.Details
import com.example.raphamovies.network.NetworkResponse
import com.example.raphamovies.network.TmdbApi
import com.example.raphamovies.network.model.dto.MovieDTO
import com.example.raphamovies.network.model.dto.VideoDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(val context: Context, private val tmdbApi: TmdbApi, @MainDispatcher val mainDispatcher: CoroutineDispatcher) : ViewModel(){

    private val _movie: MutableLiveData<Details> = MutableLiveData()
    val movie: LiveData<Details> = _movie

    private val _recommendedMovies: MutableLiveData<List<MovieDTO>> = MutableLiveData()
    val recommendedMovies: LiveData<List<MovieDTO>> = _recommendedMovies

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetails(id: Int) {
        _isLoading.value = true
        viewModelScope.launch(mainDispatcher) {
            val response = tmdbApi.getDetails(id, appConstants.VIDEOS_AND_CASTS)
            when (response) {
                is NetworkResponse.Success -> {
                    val details = response.body.toDetails()
                    _movie.value = details

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





//    private fun filterResultsList(list: Collection<DomainMovie>) {
//        val list = ArrayList<MovieDTO>()
//        list.addAll(t.results)
//
//        if (list.size == 20) {
//            val last = list.size - 1
//            val beforeLast = list.size - 2
//            list.remove()
//            list.removeAt(beforeLast)
//        }
//            _recommendedMovies.value = list
//    }

}