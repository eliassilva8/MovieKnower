package com.ems.movieknower.data

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import com.ems.movieknower.BuildConfig
import com.ems.movieknower.databinding.FragmentMovieDetailsBinding
import com.ems.movieknower.databinding.FragmentMoviesListBinding
import com.ems.movieknower.movieDetails.SimilarMoviesAdapter
import com.ems.movieknower.moviesList.MoviesListAdapter
import com.ems.movieknower.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val themoviedbKey = BuildConfig.ApiKey
val apiKey = "api_key"
val movieService = ServiceBuilder().movieService()

class ApiCall(val binding: ViewDataBinding) {
    companion object {
        var moviesList: List<Movie>? = null
    }

    /**
     * Gets a list of movies by it name
     */
    fun movieByName(movieName: String) {
        val options = hashMapOf<String, String>()
        options.put("query", movieName)
        options.put(apiKey, themoviedbKey)

        val request = movieService.getMovie(options)

        getDataFromWebService(request)
    }

    /**
     * Gets a list of movies sorted by the preferences defined
     */
    fun moviePref(prefs: HashMap<String, String?>) {
        val request = movieService.getMoviePref(prefs)
        getDataFromWebService(request)
    }

    /**
     * Gets a list of similar movies
     */
    fun similarMovies(movieId: String?) {
        val request = movieService.getSimilarMovies(movieId, themoviedbKey)

        getDataFromWebService(request)
    }

    /**
     * Set a list of movies from the themoviedb api to the fragment_movies_list
     */
    private fun getDataFromWebService(request: Call<Results>) {
        request.enqueue(object : Callback<Results> {
            override fun onFailure(call: Call<Results>, t: Throwable) {
                Log.e("ViewModel", "Fail to get movies!")
                //TODO Handle error getting response
            }

            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                if (response.isSuccessful) {
                    if (binding is FragmentMoviesListBinding) {
                        moviesList = response.body()!!.moviesResult
                        val adapter = MoviesListAdapter(binding.moviesGrid.context)
                        adapter.setMovies(moviesList!!)
                        binding.moviesGrid.adapter = adapter
                        binding.progressBar.visibility = View.GONE
                        binding.moviesGrid.visibility = View.VISIBLE
                    } else if (binding is FragmentMovieDetailsBinding) {
                        val adapter =
                            SimilarMoviesAdapter(binding.similarMoviesRv.context, response.body()!!.moviesResult)
                        binding.similarMoviesRv.adapter = adapter
                    }
                } else {
                    //TODO Handle error getting response
                }
            }
        })
    }
}