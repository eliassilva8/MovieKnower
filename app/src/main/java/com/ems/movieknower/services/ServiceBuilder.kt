package com.ems.movieknower.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder () {
    val url: String = "https://api.themoviedb.org/3/"
    val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttp: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logger)

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(url).addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build()).build()

    /**
     * Creates the specified service
     */
    fun movieService(): MovieService = retrofit.create(MovieService::class.java)
}