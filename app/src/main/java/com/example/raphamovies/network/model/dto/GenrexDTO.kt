package com.example.raphamovies.network.model.dto


import com.google.gson.annotations.SerializedName

data class GenrexDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)