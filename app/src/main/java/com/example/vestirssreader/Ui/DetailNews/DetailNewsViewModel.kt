package com.example.vestirssreader.Ui.DetailNews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vestirssreader.Data.Database.Enity.NewsItem

import com.example.vestirssreader.Data.Repository.NewsRepository




class DetailNewsViewModel(
    private val newsRepository: NewsRepository
):ViewModel() {


     val liveData = MutableLiveData<NewsItem>()
        get() = field

    override fun onCleared() {
        super.onCleared()

    }
}