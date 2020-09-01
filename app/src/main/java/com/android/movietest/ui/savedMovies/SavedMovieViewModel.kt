package com.android.movietest.ui.savedMovies

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.movietest.roomdb.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedMovieViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private val fruitList: MutableLiveData<List<String>>? = null

    var movieSelectList: MutableLiveData<List<MovieSelectedModel>> = MutableLiveData()
    var seriesSelectList: MutableLiveData<List<SeriesSelectedModel>> = MutableLiveData()

    private var db: AppDatabase? = null
    private var movieDao: MovieDao? = null
    private var seriesDao: SeriesDao? = null

    @SuppressLint("CheckResult")
    fun getMovieData(): MutableLiveData<List<MovieSelectedModel>> {

        Observable.fromCallable {

            db = context?.let { AppDatabase.getAppDataBase(context = it) }
            movieDao = db?.movieListDao()

            movieSelectList.postValue(movieDao?.getSelectedMovies())

        }.doOnNext {

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        return movieSelectList

    }

    @SuppressLint("CheckResult")
    fun getSeriesData(): MutableLiveData<List<SeriesSelectedModel>> {

        Observable.fromCallable {

            db = context?.let { AppDatabase.getAppDataBase(context = it) }
            seriesDao = db?.seriesListDao()

            seriesSelectList.postValue(seriesDao?.getSelectedSeries())

        }.doOnNext {

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        return seriesSelectList

    }

    fun deleteMovieModel(movieModel : MovieSelectedModel){

        viewModelScope.launch(Dispatchers.IO){
            Observable.fromCallable {

                db = context?.let { AppDatabase.getAppDataBase(context = it) }

                with(movieDao) {
                    this?.deleteMovie(movieModel)
                }

                movieSelectList.postValue(movieDao?.getSelectedMovies())

            }.doOnNext {

            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
    }


    fun deleteSeriesModel(seriesSelectedModel : SeriesSelectedModel){

        viewModelScope.launch(Dispatchers.IO){
            Observable.fromCallable {

                db = context?.let { AppDatabase.getAppDataBase(context = it) }

                with(seriesDao) {
                    this?.deleteSeries(seriesSelectedModel)
                }

                seriesSelectList.postValue(seriesDao?.getSelectedSeries())

            }.doOnNext {

            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
    }

}