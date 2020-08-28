package com.android.movietest.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.movietest.R
import com.android.movietest.adapter.MovieListAdapter
import com.android.movietest.adapter.SeriesListAdapter
import com.android.movietest.model.Json4Kotlin_Base
import com.android.movietest.model.Results
import com.android.movietest.roomdb.AppDatabase
import com.android.movietest.roomdb.MovieDao
import com.android.movietest.roomdb.MovieSelectedModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var rv_movie_list: RecyclerView
    private lateinit var rv_series : RecyclerView
    private lateinit var movie_list: List<Results>
    private lateinit var series_list: List<Results>
    private var movieResponseObserver: Observer<Json4Kotlin_Base>? = null
    private var seriesResponseObserver: Observer<Json4Kotlin_Base>? = null

    private var db: AppDatabase? = null
    private var movieDao: MovieDao? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        rv_movie_list  = root.findViewById(R.id.rv_movie_list)
        rv_series = root.findViewById(R.id.rv_series_list)
        movie_list = ArrayList<Results>()

      /*  val movieModel = Movie("lock", "xyz")
        Movie_list.add(movieModel)
        Movie_list.add(movieModel)
        Movie_list.add(movieModel)
        Movie_list.add(movieModel)
        Movie_list.add(movieModel)*/

        getMoviesData();

        getSeriesData();

        /*  homeViewModel.text.observe(viewLifecycleOwner, Observer {
              textView.text = it
          })*/

        return root
    }


    private fun getMoviesData() {

            if (movieResponseObserver == null) {
                movieResponseObserver = Observer<Json4Kotlin_Base> { response ->

                    Log.e("ResponseCheck", response.page.toString())

                    movie_list = response.results.toList();

                    rv_movie_list.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

                    val adapter = context?.let { it ->
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
                    rv_movie_list.adapter = adapter

                }

            }
                 homeViewModel.getMovieData().observe(viewLifecycleOwner, movieResponseObserver!!)
        }


    private fun getSeriesData(){

        if (seriesResponseObserver == null) {
            seriesResponseObserver = Observer<Json4Kotlin_Base> { response ->

                Log.e("ResponseCheck", response.page.toString())

                series_list = response.results.toList();

                rv_series.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                val adapter = context?.let { SeriesListAdapter(series_list, it) }
                rv_series.adapter = adapter

            }

        }

        homeViewModel.getSeriesData().observe(viewLifecycleOwner, seriesResponseObserver!!)

    }

}
