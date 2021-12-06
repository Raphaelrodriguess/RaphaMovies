package com.example.raphamovies.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.raphamovies.appConstants
import com.example.raphamovies.R
import com.example.raphamovies.network.model.dto.MovieDTO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieAdapter(val movieClick: OnMovieClick, val context: Context?, val movies: List<MovieDTO>)
    : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false)
        return MovieHolder(view)
    }

    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cardView: CardView = itemView.cv_movie
        var poster: ImageView = itemView.iv_movie_poster

    }

    override fun getItemCount(): Int {
        return movies.size
    }


    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        movies.elementAt(position).apply {
            Picasso.get()
                .load("${appConstants.TMDB_IMAGE_BASE_URL_W185}${poster_path}")
                .placeholder(R.drawable.placeholder)
                .into(holder.poster)
            holder.cardView.setOnClickListener {
                id?.let { id -> movieClick.onClick(id) }

            }
        }


    }

}