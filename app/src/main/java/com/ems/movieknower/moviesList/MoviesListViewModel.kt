package com.ems.movieknower.data

import androidx.databinding.BaseObservable
import com.ems.movieknower.BR


class MoviesListViewModel : BaseObservable() {
    var movie: Movie? = null
        set(movie) {
            field = movie
            notifyPropertyChanged(BR.movie)
        }
}
