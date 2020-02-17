package com.example.vestirssreader.Ui.AllNews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.vestirssreader.Data.Local.NewsItem
import com.example.vestirssreader.Data.NetworkState
import com.example.vestirssreader.Data.Repository.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AllNewsViewModel(
    private val newsRepository: NewsRepository
):ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _networkState = MutableLiveData<Pair<NetworkState,String>>()

    val networkState:LiveData<Pair<NetworkState,String>>
        get() = _networkState

    private val _rssResponse = MutableLiveData<List<NewsItem>>()

    val news:LiveData<List<NewsItem>>
        get() = _rssResponse

    init {
        getNews()
    }

    fun getNews() {
        _networkState.postValue(Pair(NetworkState.LOADING,""))
        val disposable1 = newsRepository.getNews()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            _rssResponse.postValue(it)
            _networkState.postValue(Pair(NetworkState.SUCCESS,""))
            Log.d("kek1",  it.size.toString())

        }, {
            _networkState.postValue(Pair(NetworkState.FAILURE,
                it.message?:it.toString()))
            Log.d("kek1",  it.message)
        })
        compositeDisposable.add(disposable1)
    }

    fun getNewsWithCategory(category:String) {
        _networkState.postValue(Pair(NetworkState.LOADING,""))

        val disposable1 = newsRepository.getNewsWithCategory(category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("kek2",  it.size.toString())
                _rssResponse.postValue(it)
                _networkState.postValue(Pair(NetworkState.SUCCESS,""))
            }, {
                _networkState.postValue(Pair(NetworkState.FAILURE,
                    it.message?:it.toString()))
                Log.d("kek2",  it.message)
            })

        compositeDisposable.add(disposable1)
    }



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}