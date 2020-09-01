package com.android.movietest.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieSelectedModel (@PrimaryKey(autoGenerate = true)
                            val movieId: Int, val original_title : String, val image_path : String,
                               val info :String,val vote_average : Double)
@Entity
data class SeriesSelectedModel (@PrimaryKey(autoGenerate = true)
                                val seriesId: Int, val original_title : String, val image_path : String,
                                val info :String,val vote_average : Double)