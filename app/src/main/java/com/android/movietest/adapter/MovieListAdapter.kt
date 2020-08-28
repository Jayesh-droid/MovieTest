package com.android.movietest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.movietest.R
import com.android.movietest.model.Results
import com.android.movietest.roomdb.MovieSelectedModel
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class MovieListAdapter(private var movieList: List<Results>, var context: Context,
                       val listener: (Results) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(

        parent: ViewGroup,
        viewType: Int
    ): MovieListAdapter.CustomViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_movie, parent, false)
        return CustomViewHolder(v)
    }

    override fun onBindViewHolder(holder: MovieListAdapter.CustomViewHolder, position: Int) {

        val image_base_url: String = "https://image.tmdb.org/t/p/w200"
        holder.bindItems()
        holder.itemView.setOnClickListener { listener(movieList[position]) }
        holder.name.text = movieList[position].original_title
        Picasso.get()
            .load(image_base_url + movieList[position].poster_path)
            .into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var name: TextView
        lateinit var imageView: RoundedImageView

        fun bindItems() {

            name = itemView.findViewById(R.id.tv_name) as TextView
            imageView = itemView.findViewById(R.id.movie_image_view) as RoundedImageView

        }

    }
}