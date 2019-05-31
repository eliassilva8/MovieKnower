package com.ems.movieknower.services

import com.ems.movieknower.data.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    /**
     * Gets a list of movies sorted by the specified filter
     */
    @GET("movie/{sortBy}")
    fun getMovies(@Path("sortBy") sortBy: String,
                  @Query("api_key") key: String): Call<Results>
}