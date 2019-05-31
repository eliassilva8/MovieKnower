package com.ems.movieknower.data

import android.util.Log
import com.ems.movieknower.BuildConfig
import com.ems.movieknower.databinding.MoviesListActivityBinding
import com.ems.movieknower.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val themoviedbKey = BuildConfig.ApiKey
val apiKey = "api_key"
val movieService = ServiceBuilder().movieService()

class ApiCall (val binding: MoviesListActivityBinding) {

    /**
     * Gets a list of movies by it name
     */
    fun movieByName(movieName: String) {
        var options = hashMapOf<String, String>()
        options.put("query", movieName)
        options.put(apiKey, themoviedbKey)

        val request = movieService.getMovie(options)

        getDataFromWebService(request)
    }

    /**
     * Gets a list of movies sorted by the filter defined
     */
    fun moviesSortedBy(filter: String) {
        val request = movieService.getMoviesBy(filter, themoviedbKey)
        getDataFromWebService(request)
    }

    /**
     * Set a list of movies from the themoviedb api to the movies_list_activity
     */
    private fun getDataFromWebService(request: Call<Results>) {
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