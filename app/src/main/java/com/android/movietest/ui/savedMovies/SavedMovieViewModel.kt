package com.android.movietest.ui.savedMovies

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.movietest.roomdb.AppDatabase
import com.android.movietest.roomdb.MovieDao
import com.android.movietest.roomdb.MovieSelectedModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SavedMovieViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private val fruitList: MutableLiveData<List<String>>? = null

    var movieSelectList: MutableLiveData<List<MovieSelectedModel>> = MutableLiveData()

    private var db: AppDatabase? = null
    private var movieDao: MovieDao? = null

    @SuppressLint("CheckResult")
    fun getMovieData(): MutableLiveData<List<MovieSelectedModel>> {

        Observable.fromCallable {

            db = context?.let { AppDatabase.getAppDataBase(context = it) }
            movieDao = db?.movieListDao()

            movieSelectList.postValue(db?.movieListDao()?.getSelectedMovies())

            var dbMovieList : List<MovieSelectedModel>? = db?.movieListDao()?.getSelectedMovies()

           /* if(dbMovieList != null) {
                Log.e("sizeofDBTable", dbMovieList.size.toString())
                for(i in 1 until dbMovieList.size){
                    Log.e("DB Data", dbMovieList[i].original_title)
                }
            }*/

        }.doOnNext {

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        return movieSelectList
    }

}