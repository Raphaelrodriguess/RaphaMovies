package com.example.raphamovies.dto

import com.example.raphamovies.network.model.dto.VideoDTO
import com.google.gson.annotations.SerializedName

data class VideosDTO(
    @SerializedName("results")
    val results: ArrayList<VideoDTO>)