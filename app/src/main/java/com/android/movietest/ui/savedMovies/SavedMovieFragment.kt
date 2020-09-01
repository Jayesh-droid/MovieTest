package com.android.movietest.ui.savedMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.movietest.R
import com.android.movietest.adapter.SavedMovieListAdapter
import com.android.movietest.adapter.SavedSeriesListAdapter
import com.android.movietest.roomdb.MovieSelectedModel
import com.android.movietest.roomdb.SeriesSelectedModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class SavedMovieFragment : Fragment() {

    private lateinit var savedMovieViewModel: SavedMovieViewModel
    private lateinit var rv_movie_list: RecyclerView
    private lateinit var movie_list: List<MovieSelectedModel>
    private lateinit var rv_series_list: RecyclerView
    private lateinit var series_list: List<SeriesSelectedModel>

    private var movieListObserver: Observer<List<MovieSelectedModel>>? = null
    private var seriesListObserver: Observer<List<SeriesSelectedModel>>? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        savedMovieViewModel = ViewModelProviders.of(this).get(SavedMovieViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_saved_movie, container, false)
        rv_movie_list  = root.findViewById(R.id.rv_movie_list)
        rv_series_list = root.findViewById(R.id.rv_series_list)

        getMoviesData();

        getSeriesData();

        return root
    }


    private fun getMoviesData() {

        if (movieListObserver == null) {
            movieListObserver = Observer<List<MovieSelectedModel>> { list ->

                movie_list = list

                rv_movie_list.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

                val adapter = SavedMovieListAdapter(movie_list,
                    object : SavedMovieListAdapter.OnItemClickListener{

                        override fun OnItemClickListener(movieList: MovieSelectedModel) {
                            showBottomSheet(movieList)
                        }

                        override fun OnStarClickListener(movieList: MovieSelectedModel) {
                            savedMovieViewModel.deleteMovieModel(movieList)
                        }

                    })

                rv_movie_list.adapter = adapter

            }

        }

        savedMovieViewModel.getMovieData().observe(viewLifecycleOwner, movieListObserver!!)

    }

    private fun getSeriesData() {

        if (seriesListObserver == null) {
            seriesListObserver = Observer<List<SeriesSelectedModel>> { list ->

                series_list = list

                rv_series_list.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

                val adapter = SavedSeriesListAdapter(series_list,
                    object : SavedSeriesListAdapter.OnItemClickListener{
                        override fun onItemClickListener(seriesListModel: SeriesSelectedModel) {
                            showBottomSheetSeries(seriesListModel)
                        }

                        override fun onStarClickListener(seriesListModel: SeriesSelectedModel) {
                            savedMovieViewModel.deleteSeriesModel(seriesListModel)
                        }


                    })

                rv_series_list.adapter = adapter

            }

        }

        savedMovieViewModel.getSeriesData().observe(viewLifecycleOwner, seriesListObserver!!)

    }

    private fun showBottomSheet(movieModel: MovieSelectedModel) {

        val image_base_url: String = "https://image.tmdb.org/t/p/w500"

        val view: View = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)

        val iv_movie_poster = view.findViewById<RoundedImageView>(R.id.movie_image_view)
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val tv_description = view.findViewById<TextView>(R.id.tv_description)
        val rb_rating = view.findViewById<RatingBar>(R.id.rb_rating)

        Picasso.get().load(image_base_url + movieModel.image_path)
            .into(iv_movie_poster)

        tv_title.text = movieModel.original_title
        tv_description.text = movieModel.info
        rb_rating.rating = movieModel.vote_average.toFloat()

        dialog.show()

    }

    private fun showBottomSheetSeries(seriesModel: SeriesSelectedModel) {

        val image_base_url: String = "https://image.tmdb.org/t/p/w500"

        val view: View = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)

        val iv_movie_poster = view.findViewById<RoundedImageView>(R.id.movie_image_view)
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val tv_description = view.findViewById<TextView>(R.id.tv_description)
        val rb_rating = view.findViewById<RatingBar>(R.id.rb_rating)

        Picasso.get().load(image_base_url + seriesModel.image_path)
            .into(iv_movie_poster)

        tv_title.text = seriesModel.original_title
        tv_description.text = seriesModel.info
        rb_rating.rating = seriesModel.vote_average.toFloat()

        dialog.show()

    }


}