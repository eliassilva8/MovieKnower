package com.ems.movieknower

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ems.movieknower.data.Movie
import com.ems.movieknower.data.MovieViewModel
import com.ems.movieknower.databinding.MovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {
    lateinit var binding: MovieDetailsBinding
    var movie: Movie? = null
    private val image_backdrop_size = "w300/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.movie_details)

        setUpToolbar()
        setUpMovieView()
        loadPoster(binding.toolbarMoviePoster, movie?.backdrop, image_backdrop_size)

        binding.favouriteBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Checked", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Unchecked", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setUpMovieView() {
        val bundle = intent.extras?.getBundle(getString(R.string.movie_intent))
        movie = bundle?.getParcelable(getString(R.string.movie_intent))
        binding.movieView = MovieViewModel()
        binding.movieView!!.movie = movie
    }

    private fun setUpToolbar() {
        var toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
    }
}