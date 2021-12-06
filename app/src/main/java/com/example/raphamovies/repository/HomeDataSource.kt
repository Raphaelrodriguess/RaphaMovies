package com.example.raphamovies.repository


import com.example.raphamovies.network.ErrorResponse
import com.example.raphamovies.network.NetworkResponse
import com.example.raphamovies.network.model.dto.MovieDTO
import kotlinx.coroutines.CoroutineDispatcher

interface HomeDataSource {
    suspend fun getListsOfMovies(dispatcher: CoroutineDispatcher, homeResultCallback: (result: NetworkResponse<List<List<MovieDTO>>, ErrorResponse>) -> Unit)
}