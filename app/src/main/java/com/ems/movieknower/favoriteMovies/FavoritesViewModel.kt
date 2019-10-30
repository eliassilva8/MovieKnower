package com.ems.movieknower.favoriteMovies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ems.movieknower.data.Movie
import com.ems.movieknower.database.FavoritesDao
import com.ems.movieknower.database.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoritesViewModel(val database: FavoritesDao, application: Application) :
    AndroidViewModel(application) {
    private val repository: FavoritesRepository
    private var viewModelJob = Job()
    var allFavourites: LiveData<List<Movie>?>

    init {
        repository = FavoritesRepository(database)
        allFavourites = repository.allFavorites
    }

    /*fun isFavorite(id: String): Boolean {
        return allFavourites?.find{it.id.equals(id)} != null
    }*/

    fun insert(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(movie)
    }

    fun delete(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(movie)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}