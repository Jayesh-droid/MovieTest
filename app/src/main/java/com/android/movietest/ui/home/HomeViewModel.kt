package com.android.movietest.ui.home

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.movietest.model.Json4Kotlin_Base
import com.android.movietest.retrofit.ApiClient
import com.android.movietest.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    var movieListResponse: MutableLiveData<Json4Kotlin_Base> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getMovieData(): MutableLiveData<Json4Kotlin_Base> {

        val client: ApiInterface = ApiClient.getClient

        client.getMovieData()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableSingleObserver<Json4Kotlin_Base?>() {
                override fun onSuccess(response: Json4Kotlin_Base) {
                    movieListResponse.postValue(response)
                }

                override fun onError(e: Throwable) {
                    movieListResponse.postValue(null)
                }
            })
        return movieListResponse
    }


    /* private val _text = MutableLiveData<String>().apply {
        value = "This is Home Screen"
    }
    val text: LiveData<String> = _text*/
}


