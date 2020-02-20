package com.example.vestirssreader.Ui.AllNews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vestirssreader.Data.Database.Enity.NewsItem

import com.example.vestirssreader.Data.AnswerState
import com.example.vestirssreader.Data.Repository.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AllNewsViewModel(
    private val newsRepository: NewsRepository
):ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _networkState = MutableLiveData<Pair<AnswerState,String>>()

    val answerState:LiveData<Pair<AnswerState,String>>
        get() = _networkState

    private val _rssResponse = MutableLiveData<List<NewsItem>>()

    val news:LiveData<List<NewsItem>>
        get() = _rssResponse


    val rotation = MutableLiveData<Boolean>()
        get() = field

    init {
        rotation.postValue(false)
    }

    fun getNews() {
        _networkState.postValue(Pair(AnswerState.LOADING,""))
        val disposable1 = newsRepository.getNews()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            _rssResponse.postValue(it)
            _networkState.postValue(Pair(AnswerState.SUCCESS,
                it.isEmpty().toString()))

        }, {
            _rssResponse.postValue(emptyList())
            _networkState.postValue(Pair(AnswerState.FAILURE,
                it.message?:it.toString()))
        })
        compositeDisposable.add(disposable1)
    }

    fun getNewsWithCategory(category:String) {
        Log.d("kek", category)
        _networkState.postValue(Pair(AnswerState.LOADING,""))
        val disposable1 = newsRepository.getNewsWithCategory(category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _rssResponse.postValue(it)
                _networkState.postValue(Pair(AnswerState.SUCCESS,
                    it.isEmpty().toString()))
            }, {
                _rssResponse.postValue(emptyList())
                _networkState.postValue(Pair(AnswerState.FAILURE,
                    it.message?:it.toString()))
            }, {})

        compositeDisposable.add(disposable1)
    }



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}