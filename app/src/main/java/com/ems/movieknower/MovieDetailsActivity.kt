package com.ems.movieknower

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ems.movieknower.data.ApiCall
import com.ems.movieknower.data.Movie
import com.ems.movieknower.data.MovieViewModel
import com.ems.movieknower.data.OnClickMovieHandler
import com.ems.movieknower.databinding.MovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity(), OnClickMovieHandler {
    lateinit var binding: MovieDetailsBinding
    lateinit var apiCall: ApiCall
    var movie: Movie? = null
    private val image_backdrop_size = "w300/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.movie_details)

        setUpMovieView()
        setUpRecyclerView(binding)
        loadPoster(binding.toolbarMoviePoster, movie?.backdrop, image_backdrop_size)

        binding.favouriteBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Checked", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Unchecked", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onClickMovie(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(getString(R.string.movie_intent), movie)
        intent.putExtra(getString(R.string.movie_intent), bundle)
        startActivity(intent)
    }

    private fun setUpRecyclerView(binding: MovieDetailsBinding) {
        val layoutManager = LinearLayoutManager(
            binding.similarMoviesRv.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.similarMoviesRv.layoutManager = layoutManager

        apiCall = ApiCall(binding)
        apiCall.similarMovies(movie?.id)
    }

    private fun setUpMovieView() {
        val bundle = intent.extras?.getBundle(getString(R.string.movie_intent))
        movie = bundle?.getParcelable(getString(R.string.movie_intent))
        binding.movieView = MovieViewModel()
        binding.movieView!!.movie = movie
    }
}