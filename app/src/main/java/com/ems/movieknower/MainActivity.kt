package com.ems.movieknower

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.ems.movieknower.data.Movie
import com.ems.movieknower.data.OnClickMovieHandler

class MainActivity : AppCompatActivity(), OnClickMovieHandler {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navController = this.findNavController(R.id.nav_host_fragment)
        //findViewById<BottomNavigationView>(R.id.bottom_nav)
        //.setupWithNavController(navController)
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

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bottom_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }*/
}
