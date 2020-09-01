package com.android.movietest.ui.home

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.movietest.model.MovieModel
import com.android.movietest.model.MovieResponse
import com.android.movietest.retrofit.ApiClient
import com.android.movietest.retrofit.ApiInterface
import com.android.movietest.roomdb.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    var movieListResponse: MutableLiveData<MovieResponse> = MutableLiveData()
    var seriesListResponse: MutableLiveData<MovieResponse> = MutableLiveData()

    private var db: AppDatabase? = null
    private var seriesDao: SeriesDao? = null

    @SuppressLint("CheckResult")
    fun getMovieData(): MutableLiveData<MovieResponse> {

        val client: ApiInterface = ApiClient.getClient

        client.getMovieData()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableSingleObserver<MovieResponse?>() {

                override fun onSuccess(response: MovieResponse) {
                    movieListResponse.postValue(response)
                }

                override fun onError(e: Throwable) {
                    movieListResponse.postValue(null)
                }

            })
        return movieListResponse
    }

    @SuppressLint("CheckResult")
    fun getSeriesData(): MutableLiveData<MovieResponse> {

        val client: ApiInterface = ApiClient.getClient

        client.getSeriesData()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableSingleObserver<MovieResponse?>() {

                override fun onSuccess(response: MovieResponse) {
                    seriesListResponse.postValue(response)
                }

                override fun onError(e: Throwable) {
                    seriesListResponse.postValue(null)
                }

            })

        return seriesListResponse

    }

    fun insertSeriesModel(series : MovieModel){

        viewModelScope.launch(Dispatchers.IO){

            db = context?.let { AppDatabase.getAppDataBase(context = it) }
            seriesDao = db?.seriesListDao()

            var seriesModel = SeriesSelectedModel(series.id, series.original_name, series.poster_path,
                series.overview,series.vote_average)

            with(seriesDao) {
                this?.insertSeries(seriesModel)
            }

        }


    }

}


