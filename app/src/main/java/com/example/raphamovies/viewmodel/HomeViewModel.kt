package com.example.raphamovies.viewmodel

import android.telecom.Call
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raphamovies.appConstants
import com.example.raphamovies.domainmodel.Details
import com.example.raphamovies.network.NetworkResponse
import com.example.raphamovies.network.model.dto.MovieDTO
import com.example.raphamovies.repository.HomeDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel constructor(private val homeDataSource: HomeDataSource): ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO
    private val _listsOfMovies: MutableLiveData<List<List<MovieDTO>>>? = MutableLiveData()
    val listsOfMovies: LiveData<List<List<MovieDTO>>>? = _listsOfMovies

    private val _errorMessage: MutableLiveData<String>? = MutableLiveData()
    val errorMessage: LiveData<String>? = _errorMessage

    private val _errorMessageVisibility: MutableLiveData<Boolean>? = MutableLiveData()
    val errorMessageVisibility: LiveData<Boolean>? = _errorMessageVisibility

    private val _isLoading: MutableLiveData<Boolean>? = MutableLiveData()
    val isLoading: LiveData<Boolean>? = _isLoading

    private var _errorScreenVisibility = MutableLiveData<Boolean>(false)
    var errorScreenVisibility: LiveData<Boolean> = _errorScreenVisibility

    init {
        getListsOfMovies()
    }

    fun getListsOfMovies() {
        showErrorMessage(false)
        try {
            viewModelScope.launch() {
                homeDataSource.getListsOfMovies(Dispatchers.IO) { result ->
                    when (result) {
                        is NetworkResponse.Success -> {
                            _listsOfMovies?.postValue(result.body)
                            _isLoading?.postValue(false)
                            _errorMessageVisibility?.postValue(false)
                        }
                        is NetworkResponse.NetworkError -> {
                            showErrorMessage(true, appConstants.NETWORK_ERROR_MESSAGE)
                        }
                        is NetworkResponse.ApiError -> {
                            showErrorMessage(true, appConstants.API_ERROR_MESSAGE)
                        }
                        is NetworkResponse.UnknownError -> {
                            showErrorMessage(true, appConstants.UNKNOWN_ERROR_MESSAGE)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }



    private fun showErrorMessage(show: Boolean, message: String? = null) {
        _isLoading?.postValue(!show)
        _errorMessageVisibility?.postValue(show)
        _errorMessage?.postValue(message)
    }
}