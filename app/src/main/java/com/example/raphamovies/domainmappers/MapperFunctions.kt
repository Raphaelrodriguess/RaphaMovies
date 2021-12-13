package com.example.raphamovies.domainmappers

import com.example.raphamovies.domainmodel.Details
import com.example.raphamovies.dto.CastDTO
import com.example.raphamovies.dto.CrewDTO
import com.example.raphamovies.network.model.dto.DetailsDTO


fun DetailsDTO.toDetails(): Details {
    return with(this) {
        Details(
            backdrop_path = backdropPath,
            genres = genres,
            id = id,
            overview = overview,
            poster_path = posterPath.toString(),
            release_date = releaseDate,
            runtime = runtime.toString(),
            title = title,
            vote_average =voteAverage,
            videos = video?.results,
            casts = casts
        )
    }
}

fun ArrayList<CrewDTO>.toCastDTO(): List<CastDTO> {
    return this.map { crewDTO ->
        CastDTO(
            id = crewDTO.id,
            name = crewDTO.name,
            character = crewDTO.job,
            creditId = crewDTO.creditId,
            gender = crewDTO.gender,
            castId = null,
            order = null,
            profilePath = crewDTO.profilePath
        )
    }
}