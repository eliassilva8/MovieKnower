package com.ems.movieknower.data

import android.view.View

interface OnClickMovieHandler {
    fun onClickMovie(movie: Movie, view: View)
}