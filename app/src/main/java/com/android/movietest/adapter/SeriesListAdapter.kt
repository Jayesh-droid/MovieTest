package com.android.movietest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.movietest.databinding.RowSeriesBinding
import com.android.movietest.model.MovieModel

class SeriesListAdapter(private var seriesList: List<MovieModel>,
                        val listener: OnItemClickListener) :
    RecyclerView.Adapter<SeriesListAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeriesListAdapter.CustomViewHolder {

        return CustomViewHolder(RowSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SeriesListAdapter.CustomViewHolder, position: Int) {

        holder.bind(seriesList[position],position)

    }

    override fun getItemCount(): Int {
        return seriesList.size
    }

   inner class CustomViewHolder(val binding: RowSeriesBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(series : MovieModel,postion : Int) {
            binding.series = series;
            binding.executePendingBindings()
            binding.ivStar.setOnClickListener {
                listener.onStarClicked(seriesList[postion])
            }
            itemView.setOnClickListener {
                listener.onItemClicked(seriesList[postion])
            }

        }

    }

    interface OnItemClickListener {
        fun onStarClicked(result: MovieModel)
        fun onItemClicked(result: MovieModel)
    }
}