package com.android.movietest.roomdb

import androidx.room.*

@Dao
interface SeriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeries(series: SeriesSelectedModel)

    @Delete
    fun deleteSeries(series: SeriesSelectedModel)

    @Query("SELECT * FROM SeriesSelectedModel")
    fun getSelectedSeries(): List<SeriesSelectedModel>

}