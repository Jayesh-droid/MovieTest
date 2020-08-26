package com.android.movietest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.movietest.R
import com.android.movietest.model.Movie

class MovieListAdapter(private var movieList: ArrayList<Movie>, var context: Context): RecyclerView.Adapter<MovieListAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(

        parent: ViewGroup,
        viewType: Int ): MovieListAdapter.CustomViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_movie, parent, false)
        return CustomViewHolder(v)

    }

    override fun onBindViewHolder(holder: MovieListAdapter.CustomViewHolder, position: Int) {

        holder.bindItems()
        holder.name.text = movieList[position].name

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var name: TextView

        fun bindItems() {

            name = itemView.findViewById(R.id.tv_name) as TextView

        }

    }
}