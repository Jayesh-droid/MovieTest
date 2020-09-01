package com.android.movietest.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.movietest.R
import com.android.movietest.adapter.MovieListAdapter
import com.android.movietest.adapter.SeriesListAdapter
import com.android.movietest.model.MovieModel
import com.android.movietest.model.MovieResponse
import com.android.movietest.roomdb.AppDatabase
import com.android.movietest.roomdb.MovieDao
import com.android.movietest.roomdb.MovieSelectedModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var homeViewModel: HomeViewModel
    private lateinit var rv_movie_list: RecyclerView
    private lateinit var rv_series: RecyclerView
    private lateinit var movie_list: List<MovieModel>
    private lateinit var series_list: List<MovieModel>
    private var movieResponseObserver: Observer<MovieResponse>? = null
    private var seriesResponseObserver: Observer<MovieResponse>? = null

    private var db: AppDatabase? = null
    private var movieDao: MovieDao? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        rv_movie_list = root.findViewById(R.id.rv_movie_list)
        rv_series = root.findViewById(R.id.rv_series_list)

        movie_list = ArrayList<MovieModel>()

        getMoviesData();

        getSeriesData();

        return root

    }

    private fun getMoviesData() {

        if (movieResponseObserver == null) {

            movieResponseObserver = Observer<MovieResponse> { response ->

                Log.e("ResponseCheck", response.page.toString())

                movie_list = response.results.toList();

                rv_movie_list.layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.HORIZONTAL,
                    false
                )

                /*   val adapter = context?.let { it ->
                       MovieListAdapter(movie_list, it) {

                           Log.e("postion", it.title)
                           Log.e("id", it.id.toString())

                           Observable.fromCallable {

                               db = activity?.let { AppDatabase.getAppDataBase(context = it) }
                               movieDao = db?.movieListDao()

                               var movie1 = MovieSelectedModel(it.id, it.title, it.poster_path)

                               with(movieDao) {
                                   this?.insertMovie(movie1)
                               }

                               var dbMovieList: List<MovieSelectedModel>? =
                                   db?.movieListDao()?.getSelectedMovies()

                               if (dbMovieList != null) {
                                   Log.e("sizeofDBTable", dbMovieList.size.toString())
                                   for (i in 1 until dbMovieList.size) {
                                       Log.e("DB Data", dbMovieList[i].original_title)
                                   }
                               }

                           }.doOnNext {

                           }.subscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe()

                       }
                   }

                   rv_movie_list.adapter = adapter*/

                val adapter = MovieListAdapter(movie_list,
                    object : MovieListAdapter.OnItemClickListener {
                        override fun onStarClicked(result: MovieModel) {

                            insertDataDB(result)

                        }

                        override fun onItemClicked(result: MovieModel) {
                            showBottomSheet(result);

                        }

                    })

                rv_movie_list.adapter = adapter
            }
        }
        homeViewModel.getMovieData().observe(viewLifecycleOwner, movieResponseObserver!!)
    }

    private fun getSeriesData() {

        if (seriesResponseObserver == null) {

            seriesResponseObserver = Observer<MovieResponse> { response ->

                Log.e("ResponseCheck", response.page.toString())

                series_list = response.results.toList();

                rv_series.layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.HORIZONTAL,
                    false
                )

                val adapter =
                    SeriesListAdapter(series_list, object : SeriesListAdapter.OnItemClickListener {
                        override fun onStarClicked(result: MovieModel) {
                            homeViewModel.insertSeriesModel(result)
                            Toast.makeText(activity,"Series added to saved list", Toast.LENGTH_SHORT).show()
                        }

                        override fun onItemClicked(series: MovieModel) {
                            showBottomSheetSeries(series)
                        }

                    })
                rv_series.adapter = adapter
            }
        }

        homeViewModel.getSeriesData().observe(viewLifecycleOwner, seriesResponseObserver!!)

    }

    //method to insert data in db
    private fun insertDataDB(result: MovieModel) {

        Toast.makeText(activity,"Movie added to saved list",Toast.LENGTH_SHORT).show()

        Observable.fromCallable {

            db = activity?.let { AppDatabase.getAppDataBase(context = it) }
            movieDao = db?.movieListDao()

            var movie1 = MovieSelectedModel(
                result.id, result.title, result.poster_path,
                result.overview, result.vote_average
            )

            with(movieDao) {
                this?.insertMovie(movie1)
            }

            var dbMovieList: List<MovieSelectedModel>? =
                db?.movieListDao()?.getSelectedMovies()

            if (dbMovieList != null) {
                Log.e("sizeofDBTable", dbMovieList.size.toString())
                for (i in 1 until dbMovieList.size) {
                    Log.e("DB Data", dbMovieList[i].original_title)
                }
            }

        }.doOnNext {


        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }

    private fun showBottomSheet(result: MovieModel) {

        val image_base_url: String = "https://image.tmdb.org/t/p/w500"

        val view: View = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)

        val iv_movie_poster = view.findViewById<RoundedImageView>(R.id.movie_image_view)
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val tv_description = view.findViewById<TextView>(R.id.tv_description)
        val rb_rating = view.findViewById<RatingBar>(R.id.rb_rating)

        Picasso.get().load(image_base_url + result.backdrop_path)
            .into(iv_movie_poster)

        tv_title.text = result.original_title
        tv_description.text = result.overview
        rb_rating.rating = result.vote_average.toFloat()

        dialog.show()
    }

    private fun showBottomSheetSeries(series: MovieModel) {

        val image_base_url: String = "https://image.tmdb.org/t/p/w500"

        val view: View = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)

        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)

        val iv_movie_poster = view.findViewById<RoundedImageView>(R.id.movie_image_view)
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val tv_description = view.findViewById<TextView>(R.id.tv_description)
        val rb_rating = view.findViewById<RatingBar>(R.id.rb_rating)

        Picasso.get().load(image_base_url + series.backdrop_path)
            .into(iv_movie_poster)

        tv_title.text = series.original_name
        tv_description.text = series.overview
        rb_rating.rating = series.vote_average.toFloat()

        dialog.show()
    }

}
