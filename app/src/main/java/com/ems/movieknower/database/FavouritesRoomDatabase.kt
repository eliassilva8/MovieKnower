package com.ems.movieknower.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ems.movieknower.data.Movie


@Database(entities = arrayOf(Movie::class), version = 1)
abstract class FavouritesRoomDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao

    //Making the FavouritesRoomDatabase a singleton to prevent having multiple instances of the database opened at the same time.
    companion object {
        var INSTANCE: FavouritesRoomDatabase? = null

        fun getDatabase(context: Context): FavouritesRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(FavouritesRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavouritesRoomDatabase::class.java,
                        "favourite_movies_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}