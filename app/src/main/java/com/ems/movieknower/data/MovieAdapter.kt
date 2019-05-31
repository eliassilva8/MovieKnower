package com.ems.movieknower.data

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ems.movieknower.BR
import com.ems.movieknower.R
import com.ems.movieknower.databinding.MovieItemBinding

class MovieAdapter(val context: Context, val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val image_base_url = "http://image.tmdb.org/t/p/"
    private val image_size = "w185/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding : MovieItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.movie_item, parent, false)
        return MovieViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Movie = movies.get(position)
        holder.binding?.setVariable(BR.movie, movie)
        holder.binding?.executePendingBindings()
        loadPoster(holder.binding!!.moviesPoster, movie.poster)
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: MovieItemBinding?
        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    private fun loadPoster(view: ImageView, path: String) {
        Glide
            .with(view.context)
            .load(image_base_url + image_size + path)
            .into(view)
    }
}