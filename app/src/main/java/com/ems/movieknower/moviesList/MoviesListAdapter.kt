package com.ems.movieknower.moviesList

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ems.movieknower.BR
import com.ems.movieknower.R
import com.ems.movieknower.data.Movie
import com.ems.movieknower.data.OnClickMovieHandler
import com.ems.movieknower.databinding.MovieItemBinding
import com.ems.movieknower.utils.loadPoster

class MoviesListAdapter(val context: Context) :
    RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {
    private val image_poster_size = "w185/"
    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: MovieItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.movie_item,
            parent,
            false
        )
        return MovieViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Movie = movies.get(position)
        setRatingCircleColor(holder.binding!!.movieRating, movie.rating)
        holder.binding?.setVariable(BR.movie, movie)
        holder.binding?.onClickMovie = context as OnClickMovieHandler
        holder.binding?.setVariable(BR.movie, movie)
        holder.binding?.executePendingBindings()
        loadPoster(holder.binding!!.moviesPoster, movie.poster, image_poster_size)
    }

    private fun setRatingCircleColor(view: TextView, rating: String?) {
        when (rating?.toFloat()) {
            null -> view.background.setColorFilter(
                context.getColor(R.color.red),
                PorterDuff.Mode.MULTIPLY
            )
            in 0.0..5.0 -> view.background.setColorFilter(
                context.getColor(R.color.red),
                PorterDuff.Mode.MULTIPLY
            )
            in 5.0..6.9 -> view.background.setColorFilter(
                context.getColor(R.color.yellow),
                PorterDuff.Mode.MULTIPLY
            )
            in 7.0..8.4 -> view.background.setColorFilter(
                context.getColor(R.color.green),
                PorterDuff.Mode.MULTIPLY
            )
            else -> view.background.setColorFilter(
                context.getColor(R.color.blue),
                PorterDuff.Mode.MULTIPLY
            )
        }

    }

    override fun getItemCount(): Int = movies.size

    internal fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: MovieItemBinding?

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }
}