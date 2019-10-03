package com.ems.movieknower

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.ems.movieknower.data.Movie
import com.ems.movieknower.data.OnClickMovieHandler

class MainActivity : AppCompatActivity(), OnClickMovieHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_layout)
    }

    override fun onClickMovie(movie: Movie, view: View) {
        when (view.id) {
            R.id.card_view_movies_list -> {
                val directions =
                    MoviesListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(movie)
                findNavController(view.id).navigate(directions)
            }
            R.id.card_view_movie_details -> {
                val directions =
                    MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(movie)
                findNavController(view.id).navigate(directions)
            }
        }
    }
}
