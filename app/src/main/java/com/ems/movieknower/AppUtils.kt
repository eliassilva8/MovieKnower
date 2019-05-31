package com.ems.movieknower

import android.util.Log
import com.ems.movieknower.data.MovieAdapter
import com.ems.movieknower.data.Results
import com.ems.movieknower.databinding.MoviesListActivityBinding
import com.ems.movieknower.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val themoviedbKey = BuildConfig.ApiKey
/**
 * Set a list of movies from the themoviedb api to the movies_list_activity
 */
public fun getDataFromWebService(binding: MoviesListActivityBinding) {
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