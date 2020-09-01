package com.android.movietest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.movietest.R
import com.android.movietest.databinding.RowMovieBinding
import com.android.movietest.databinding.RowSavedMovieBinding
import com.android.movietest.roomdb.MovieSelectedModel
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class SavedMovieListAdapter(private var movieList: List<MovieSelectedModel>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<SavedMovieListAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedMovieListAdapter.CustomViewHolder {
        return CustomViewHolder(
            RowSavedMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: SavedMovieListAdapter.CustomViewHolder, position: Int) {

        holder.bind(movieList[position],position)

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class CustomViewHolder(val binding: RowSavedMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieSelectedModel, postion: Int){
            binding.movie = movie
            binding.executePendingBindings()
            binding.ivStar.setOnClickListener {
                listener.OnStarClickListener(movieList[postion])
            }
            itemView.setOnClickListener {
                listener.OnItemClickListener(movieList[postion])
            }
        }

    }

    interface OnItemClickListener{
        fun OnItemClickListener(movieList: MovieSelectedModel)
        fun OnStarClickListener(movieList: MovieSelectedModel)
    }

}