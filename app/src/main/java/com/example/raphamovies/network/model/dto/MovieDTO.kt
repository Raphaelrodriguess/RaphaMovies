package com.example.raphamovies.network.model.dto


import com.google.gson.annotations.SerializedName

data class MovieDTO(

    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val poster_path: String,
    @SerializedName("title")
    val title: String
)