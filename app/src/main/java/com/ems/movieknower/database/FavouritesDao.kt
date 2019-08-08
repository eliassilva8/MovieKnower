package com.ems.movieknower.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ems.movieknower.data.Movie

@Dao
interface FavouritesDao {
    @Query("SELECT * from favourite_movies_table")
    fun getAllFavouriteMovies(): LiveData<List<Movie>>

    @Insert
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)
}