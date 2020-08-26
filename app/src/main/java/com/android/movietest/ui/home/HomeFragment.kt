package com.android.movietest.ui.home

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.movietest.R
import com.android.movietest.adapter.MovieListAdapter
import com.android.movietest.model.Json4Kotlin_Base
import com.android.movietest.model.Movie

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var rv_movie_list: RecyclerView
    private lateinit var Movie_list: ArrayList<Movie>
    private var responseObserver: Observer<Json4Kotlin_Base>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        rv_movie_list  = root.findViewById(R.id.rv_movie_list)
        Movie_list = ArrayList<Movie>()

        val movieModel = Movie("lock", "xyz")

        Movie_list.add(movieModel)
        Movie_list.add(movieModel)
        Movie_list.add(movieModel)
        Movie_list.add(movieModel)
        Movie_list.add(movieModel)

        rv_movie_list.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val adapter = context?.let { MovieListAdapter(Movie_list, it) }
        rv_movie_list.adapter = adapter

        getMatchesData();

        /*  homeViewModel.text.observe(viewLifecycleOwner, Observer {
              textView.text = it
          })*/

        return root
    }


    private fun getMatchesData() {

            if (responseObserver == null) {
                responseObserver = Observer<Json4Kotlin_Base> { response ->

                    Log.e("ResponseCheck", response.page.toString())


                    }


            }
    homeViewModel.getMovieData().observe(viewLifecycleOwner, responseObserver!!)
        }
}
