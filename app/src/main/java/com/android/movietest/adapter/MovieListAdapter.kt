package com.android.movietest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.movietest.databinding.RowMovieBinding
import com.android.movietest.model.MovieModel

class MovieListAdapter(private var movieList: List<MovieModel>,
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

        fun bind(result: MovieModel, position: Int) {
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
        fun onStarClicked(result: MovieModel)
        fun onItemClicked(result: MovieModel)
    }

}