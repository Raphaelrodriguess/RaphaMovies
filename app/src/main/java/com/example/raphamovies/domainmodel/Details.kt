package com.example.raphamovies.domainmodel

import android.text.SpannableStringBuilder
import androidx.core.text.bold
import com.example.raphamovies.dto.CastsDTO
import com.example.raphamovies.dto.CrewDTO
import com.example.raphamovies.network.model.dto.GenrexDTO
import com.example.raphamovies.network.model.dto.VideoDTO

data class Details(
    val backdrop_path: String?,
    val genres: List<GenrexDTO>?,
    val id: Int?,
    val overview: String?,
    val poster_path: String?,
    var release_date: String?,
    var runtime: String?,
    val title: String?,
    val vote_average: Double,
    val videos: ArrayList<VideoDTO>,
    val casts: CastsDTO?,
    var formattedCasts: SpannableStringBuilder? = null,
    var formattedDirector: SpannableStringBuilder? = null
) {
    init {
        release_date = release_date?.subSequence(0, 4).toString()

        val trailer = getTrailer()
        videos?.clear()
        trailer?.let { videos?.add(it) }

        formatCastsArray()
        formattedCasts = formatCasts(casts)
        formattedDirector = formatDirector(casts)
        runtime = formatRuntime()

    }

    private fun formatCasts(castAndDirector: CastsDTO?) : SpannableStringBuilder {
        var builder = SpannableStringBuilder()

        builder.bold { append("Cast: ") }

        if (!castAndDirector?.cast.isNullOrEmpty()) {
            for (i in 0 until castAndDirector?.cast!!.size) {
                if (i == castAndDirector.cast.size - 1)
                    builder.append("${castAndDirector.cast[i].name}")
                else
                    builder.append("${castAndDirector.cast[i].name}, ")
            }
        }

        return builder
    }

    private fun formatDirector(castAndDirector: CastsDTO?): SpannableStringBuilder {
        var builder = SpannableStringBuilder()

        builder.bold { append("Director: ") }
        builder.append("${castAndDirector?.crew?.get(0)?.name}")

        return builder
    }

    private fun formatRuntime() : String = runtime?.let {
            val duration = it.toInt()
            val minutes = (duration % 60)
            val hours = (duration / 60)
            "${hours}h ${minutes}m"
        } ?: ""


    private fun formatCastsArray() {
        if (!casts?.cast.isNullOrEmpty()){
            for (i in casts?.cast!!.size - 1 downTo 14) {
                casts.cast.removeAt(i)
            }
        }

        var director: CrewDTO? = null
        if(!casts?.crew.isNullOrEmpty()){
            casts?.crew?.forEach { crew ->
                var job = crew.job?.toLowerCase()
                if (director == null && job == "director" ) {
                    director = crew
                }
            }
            casts?.crew?.clear()
            director?.let { casts?.crew?.add(it) }
        }

    }

    private fun getTrailer(): VideoDTO? {
        var trailer: VideoDTO? = null

        videos?.forEach { video ->
            val name = video.name?.toLowerCase()
            if (name != null) {
                if (name.contains("Official Trailer"))
                    trailer = video
            }
        }

        if (trailer == null) {
            videos?.forEach { video->
                val name = video.name?.toLowerCase()
                if (name != null) {
                    if (name.contains("trailer"))
                        trailer = video
                }
            }
        }

        if (trailer == null)
            if (!videos.isNullOrEmpty())
                trailer = videos[0]

        return trailer
    }
}


