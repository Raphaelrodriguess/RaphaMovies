package com.example.raphamovies.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.raphamovies.appConstants
import com.example.raphamovies.di.MainDispatcher
import com.example.raphamovies.dto.PersonDetails
import com.example.raphamovies.network.Error
import com.example.raphamovies.network.Resource
import com.example.raphamovies.network.Resource.Companion.error
import com.example.raphamovies.network.TmdbApi
import com.example.raphamovies.network.model.dto.MovieDTO
import com.example.raphamovies.network.model.dto.MovieResponseDTO
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PersonViewModel @Inject constructor(val context: Context, private val tmdbApi: TmdbApi, @MainDispatcher val mainDispatcher: CoroutineDispatcher) : ViewModel(){
    var person: MutableLiveData<Resource<PersonDetails>>? = MutableLiveData()
    var starredMovies: MutableLiveData<Resource<List<MovieDTO>>> = MutableLiveData()

    fun getPersonDetails(id: Int) {
        tmdbApi.getPerson(id).enqueue(object : Callback<PersonDetails> {
            override fun onFailure(call: Call<PersonDetails>, t: Throwable) {
                person?.value = Resource.error(Error(400, t.localizedMessage))
            }

            override fun onResponse(call: Call<PersonDetails>, response: Response<PersonDetails>) {
                if (response.isSuccessful) {
                    response.body()?.let { personData ->
                        person?.value = Resource.success(personData)
                    }
                } else {
                    Log.d("personlog", "ViewModel, NOT Successful")
                }
            }

        })
    }

    fun getActorsMovies(id: Int) {
        tmdbApi.getActorsMovies(id, appConstants.LANGUAGE, false).enqueue(object : Callback<MovieResponseDTO> {
            override fun onFailure(call: Call<MovieResponseDTO>, t: Throwable) {
                person?.value = Resource.error(Error(400, t.localizedMessage))
            }

            override fun onResponse(call: Call<MovieResponseDTO>, response: Response<MovieResponseDTO>) {
                if (response.isSuccessful) {
                    response.body()?.let { movies ->
                        starredMovies.value = Resource.success(movies.results)
                    }
                } else {
                    Log.d("personlog", "ViewModel, NOT Successful")
                }
            }

        })
    }
}