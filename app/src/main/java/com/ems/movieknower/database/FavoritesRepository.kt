package com.ems.movieknower.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.ems.movieknower.data.Movie

class FavoritesRepository(private val favoritesDao: FavoritesDao) {
    val allFavorites = favoritesDao.getAllFavoriteMovies()

    @WorkerThread
    suspend fun findMovie(id: String): LiveData<Movie?> {
        return favoritesDao.findMovie(id)
    }

    @WorkerThread
    suspend fun insert(movie: Movie) {
        favoritesDao.insert(movie)
    }

    @WorkerThread
    suspend fun delete(movie: Movie) {
        favoritesDao.delete(movie)
    }
}