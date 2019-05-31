package com.ems.movieknower.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Results(@SerializedName("results") val moviesResult: List<Movie>) : Serializable

data class Movie(@SerializedName("id") val id: String,
            @SerializedName("poster_path") val poster: String,
            @SerializedName("title") val title: String,
            @SerializedName("release_date") val release: String,
            @SerializedName("vote_average") val rating: String,
            @SerializedName("overview") val synopsis: String,
            val isFavorite: Boolean) : Serializable