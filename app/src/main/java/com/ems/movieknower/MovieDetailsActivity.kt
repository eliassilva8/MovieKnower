package com.ems.movieknower

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.ems.movieknower.data.*
import com.ems.movieknower.database.FavouritesRoomDatabase
import com.ems.movieknower.databinding.MovieDetailsBinding
import com.ems.movieknower.utils.loadPoster

class MovieDetailsActivity : AppCompatActivity(), OnClickMovieHandler {

    lateinit var binding: MovieDetailsBinding
    lateinit var apiCall: ApiCall
    var movie: Movie? = null
    private val image_backdrop_size = "w300/"
    private lateinit var mFavouritesViewModel: FavouritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.movie_details)

        setUpMovieView()
        setUpRecyclerView(binding)
        loadPoster(binding.toolbarMoviePoster, movie?.backdrop, image_backdrop_size)
        mFavouritesViewModel = ViewModelProviders.of(this).get(FavouritesViewModel::class.java)

        val db =
            Room.databaseBuilder(applicationContext, FavouritesRoomDatabase::class.java, "favourite_movies_database")
                .build()

        //TODO Codigo para ir buscar todos os favoritos
        /*mFavouritesViewModel.allFavourites.observe(this, object : Observer<List<Movie>> {
            override fun onChanged(t: List<Movie>?) {

            }
        })*/

        binding.favouriteBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mFavouritesViewModel.insert(movie!!)
                Toast.makeText(this, getString(R.string.added_to_favourites), Toast.LENGTH_SHORT).show()
            } else {
                mFavouritesViewModel.delete(movie!!)
                Toast.makeText(this, getString(R.string.deleted_from_favourites), Toast.LENGTH_SHORT).show()
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