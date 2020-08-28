package com.android.movietest.roomdb

import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieSelectedModel)

    @Delete
    fun deleteMovie(movie: MovieSelectedModel)

    @Query("SELECT * FROM MovieSelectedModel")
    fun getSelectedMovies(): List<MovieSelectedModel>

}