package com.android.movietest.Utility

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

class Utils {

}

@BindingAdapter("bind:imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Picasso.get()
        .load("https://image.tmdb.org/t/p/w200$imageUrl")
        .into(view)
}