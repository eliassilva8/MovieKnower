package com.ems.movieknower

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.ems.movieknower.data.MovieAdapter
import com.ems.movieknower.data.Results
import com.ems.movieknower.databinding.MoviesListActivityBinding
import com.ems.movieknower.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesListActivity: AppCompatActivity() {
    val num_columns = 2
    val themoviedbKey = BuildConfig.ApiKey

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: MoviesListActivityBinding = DataBindingUtil.setContentView(this, R.layout.movies_list_activity)
        setSupportActionBar(binding.moviesListToolbar)

        setUpRecyclerView(binding)
        getMovies(binding)
    }

    private fun setUpRecyclerView(binding: MoviesListActivityBinding) {
        val layoutManager = GridLayoutManager(binding.moviesGrid.context, num_columns, GridLayoutManager.VERTICAL, false)
        binding.moviesGrid.layoutManager = layoutManager
        binding.moviesGrid.setHasFixedSize(true)
    }

    /**
     * Set a list of movies from the themoviedb api to the movies_list_activity
     */
    fun getMovies(binding: MoviesListActivityBinding) {
        val movieService = ServiceBuilder().movieService()
        val request = movieService.getMovies("popular", themoviedbKey)

        request.enqueue(object : Callback<Results> {
            override fun onFailure(call: Call<Results>, t: Throwable) {
                Log.e("ViewModel", "Fail to get movies!")
                //TODO Handle error getting response
            }

            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                if (response.isSuccessful) {
                    val adapter = MovieAdapter(binding.moviesGrid.context, response.body()!!.moviesResult)
                    binding.moviesGrid.adapter = adapter
                    Log.i("ViewModel", "Success getting movies!")
                } else {
                    //TODO Handle error getting response
                }
            }
        })
    }
}