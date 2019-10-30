package com.ems.movieknower.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ems.movieknower.data.Movie

@Dao
interface FavoritesDao {
    @Query("SELECT * from favourite_movies_table")
    fun getAllFavoriteMovies(): LiveData<List<Movie>?>

    @Query("SELECT * from favourite_movies_table WHERE id IN (:movieId)")
    fun findMovie(movieId: String): LiveData<Movie?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)

}