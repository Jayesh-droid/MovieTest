package com.android.movietest.ui.savedMovies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.movietest.R
import com.android.movietest.adapter.MovieListAdapter
import com.android.movietest.adapter.SavedMovieListAdapter
import com.android.movietest.model.Results
import com.android.movietest.roomdb.AppDatabase
import com.android.movietest.roomdb.MovieSelectedModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SavedMovieFragment : Fragment() {

    private lateinit var savedMovieViewModel: SavedMovieViewModel
    private lateinit var rv_movie_list: RecyclerView
    private lateinit var movie_list: List<MovieSelectedModel>

    var movieSelectedList: MutableLiveData<List<MovieSelectedModel>> = MutableLiveData()
    private var movieListObserver: Observer<List<MovieSelectedModel>>? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        savedMovieViewModel = ViewModelProviders.of(this).get(SavedMovieViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_saved_movie, container, false)
        rv_movie_list  = root.findViewById(R.id.rv_movie_list)

        getMoviesData();

        return root
    }


    private fun getMoviesData() {

        if (movieListObserver == null) {
            movieListObserver = Observer<List<MovieSelectedModel>> { list ->

                movie_list = list

                rv_movie_list.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

                val adapter = context?.let { SavedMovieListAdapter(movie_list, it) }
                rv_movie_list.adapter = adapter

            }

        }
        savedMovieViewModel.getMovieData().observe(viewLifecycleOwner, movieListObserver!!)
    }

}