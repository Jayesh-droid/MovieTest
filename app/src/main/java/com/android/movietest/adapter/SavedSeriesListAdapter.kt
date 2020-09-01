package com.android.movietest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.movietest.databinding.RowSavedSeriesBinding
import com.android.movietest.roomdb.SeriesSelectedModel

class SavedSeriesListAdapter(private var seriesList: List<SeriesSelectedModel>,val listener : OnItemClickListener) :
    RecyclerView.Adapter<SavedSeriesListAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedSeriesListAdapter.CustomViewHolder {
        return CustomViewHolder(
            RowSavedSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SavedSeriesListAdapter.CustomViewHolder, position: Int) {
        holder.bind(seriesList[position],position)
    }

    override fun getItemCount(): Int {
        return seriesList.size
    }

    inner class CustomViewHolder(val binding: RowSavedSeriesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(selectedModel: SeriesSelectedModel, postion: Int){
            binding.movie = selectedModel
            binding.executePendingBindings()
            binding.ivStar.setOnClickListener {
                listener.onStarClickListener(seriesList[postion])
            }
            itemView.setOnClickListener {
                listener.onItemClickListener(seriesList[postion])
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClickListener(seriesListModel: SeriesSelectedModel)
        fun onStarClickListener(seriesListModel: SeriesSelectedModel)
    }
}