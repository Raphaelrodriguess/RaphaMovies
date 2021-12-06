package com.example.raphamovies.network.model.dto


import com.google.gson.annotations.SerializedName

data class MovieResponseDTO(

    @SerializedName("results")
    val results : List<MovieDTO>
)