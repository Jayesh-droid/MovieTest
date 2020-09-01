package com.android.movietest.retrofit

import com.android.movietest.model.MovieResponse

import io.reactivex.Single
import retrofit2.http.GET

interface ApiInterface {

    @GET("3/movie/popular?api_key=60af9fe8e3245c53ad9c4c0af82d56d6&language=en-US&page=1")
    fun getMovieData(): Single<MovieResponse?>?

    @GET("3/tv/popular?api_key=60af9fe8e3245c53ad9c4c0af82d56d6&language=en-US&page=1")
    fun getSeriesData(): Single<MovieResponse?>?

}