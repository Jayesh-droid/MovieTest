package com.android.movietest.model

import com.google.gson.annotations.SerializedName

data class Movie (var name: String,var image_url: String)

data class Json4Kotlin_Base (

    @SerializedName("page") val page : Int,
    @SerializedName("total_results") val total_results : Int,
    @SerializedName("total_pages") val total_pages : Int,
    @SerializedName("results") val results : List<Results>
)

data class Results (

    @SerializedName("popularity") val popularity : Double,
    @SerializedName("vote_count") val vote_count : Int,
    @SerializedName("video") val video : Boolean,
    @SerializedName("poster_path") val poster_path : String,
    @SerializedName("id") val id : Int,
    @SerializedName("adult") val adult : Boolean,
    @SerializedName("backdrop_path") val backdrop_path : String,
    @SerializedName("original_language") val original_language : String,
    @SerializedName("original_title") val original_title : String,
    @SerializedName("genre_ids") val genre_ids : List<Int>,
    @SerializedName("title") val title : String,
    @SerializedName("vote_average") val vote_average : Double,
    @SerializedName("overview") val overview : String,
    @SerializedName("release_date") val release_date : String,
    @SerializedName("original_name") val original_name : String,
    @SerializedName("name") val name : String,
    @SerializedName("origin_country") val origin_country : List<String>,
    @SerializedName("first_air_date") val first_air_date : String,

)

