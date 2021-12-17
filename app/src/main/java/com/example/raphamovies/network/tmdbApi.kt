package com.example.raphamovies.network

import com.example.raphamovies.network.model.dto.DetailsDTO
import com.example.raphamovies.network.model.dto.MovieResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("trending/movie/day")
    suspend fun getTrending(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ): NetworkResponse<MovieResponseDTO, ErrorResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ): NetworkResponse<MovieResponseDTO, ErrorResponse>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ): NetworkResponse<MovieResponseDTO, ErrorResponse>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ): NetworkResponse<MovieResponseDTO, ErrorResponse>

    @GET("movie/{id}")
    suspend fun getDetails(
        @Path("id")
        id: Int?,
        @Query("append_to_response")
        append: String? = null
    ): NetworkResponse<DetailsDTO, Error>

}