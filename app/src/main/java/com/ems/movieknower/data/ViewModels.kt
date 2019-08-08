package com.ems.movieknower.data

import android.app.Application
import androidx.databinding.BaseObservable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ems.movieknower.BR
import com.ems.movieknower.database.FavouritesRepository
import com.ems.movieknower.database.FavouritesRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel : BaseObservable() {
    var movie: Movie? = null
        set(movie) {
            field = movie
            notifyPropertyChanged(BR.movie)
        }
}

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavouritesRepository
    val allFavourites: LiveData<List<Movie>>

    init {
        val favouritesDao = FavouritesRoomDatabase.getDatabase(application)?.favouritesDao()
        repository = FavouritesRepository(favouritesDao!!)
        allFavourites = repository.allFavouriteMovies
    }

    fun insert(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(movie)
    }

    fun delete(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(movie)
    }
}
