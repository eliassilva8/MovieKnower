package com.ems.movieknower.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.ems.movieknower.data.Movie

class FavouritesRepository(private val favouritesDao: FavouritesDao) {
    val allFavouriteMovies: LiveData<List<Movie>> = favouritesDao.getAllFavouriteMovies()

    @WorkerThread
    suspend fun insert(movie: Movie) {
        favouritesDao.insert(movie)
    }

    @WorkerThread
    suspend fun delete(movie: Movie) {
        favouritesDao.delete(movie)
    }
}