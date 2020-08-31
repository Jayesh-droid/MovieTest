package com.android.movietest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.movietest.R
import com.android.movietest.databinding.RowMovieBinding
import com.android.movietest.model.Results
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class MovieListAdapter(private var movieList: List<Results>,
                       val listener: OnItemClickListener) :
    RecyclerView.Adapter<MovieListAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {

        return CustomViewHolder(
            RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.bind(movieList[position],position)

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

   inner class CustomViewHolder(val binding: RowMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Results,position: Int) {
            binding.result = result
            binding.executePendingBindings()
            binding.ivStar.setOnClickListener {
                listener.onStarClicked(movieList[position])
            }
            itemView.setOnClickListener{
                listener.onItemClicked(movieList[position])
            }
        }

    }

    interface OnItemClickListener {
        fun onStarClicked(result: Results)
        fun onItemClicked(result: Results)
    }

}