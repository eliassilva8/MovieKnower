package com.ems.movieknower

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.ems.movieknower.data.Movie
import com.ems.movieknower.data.OnClickMovieHandler
import com.ems.movieknower.databinding.HostLayoutBinding

class MainActivity : AppCompatActivity(), OnClickMovieHandler {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<HostLayoutBinding>(this, R.layout.host_layout)
        navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
