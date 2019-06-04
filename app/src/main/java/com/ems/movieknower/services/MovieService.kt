package com.ems.movieknower.services

import com.ems.movieknower.data.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieService {
    /**
     * Gets a movie by it name
     */
    @GET("search/movie")
    fun getMovie(@QueryMap options: HashMap<String, String>): Call<Results>

    /**
     * Gets a list of movies sorted by the specified preferences
     */
    @GET("discover/movie")
    fun getMoviePref(@QueryMap options: HashMap<String, String>): Call<Results>
}