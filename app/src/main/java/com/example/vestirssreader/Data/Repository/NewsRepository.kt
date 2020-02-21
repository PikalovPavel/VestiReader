package com.example.vestirssreader.Data.Repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vestirssreader.Data.Database.Dao.NewsItemDao

import com.example.vestirssreader.Data.Database.Enity.NewsItem
import com.example.vestirssreader.Data.Remote.NewsApiService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


class NewsRepository (private val remote: NewsApiService,
                      private val local: NewsItemDao) {


    private val REMOTE = "REMOTE_ERROR"


   private fun getLocalNews(): Single<List<NewsItem>> {
        return local.getNews()

    }

    private fun getLocalNewsWithCategory(category: String): Single<List<NewsItem>> {
        return local.getCategoryNews(category)
    }

    private fun saveToDatabase(news: List<NewsItem>?) {
        if (!news.isNullOrEmpty()) {
            local.saveAllNews(news)
        }
    }

    fun getNews(): Single<List<NewsItem>> {
        return remote.getFeed()
            .map { rss ->
                rss.channel!!.items!!.map { item ->
                    item.mapToNewsItem()
                }
            }
            .doOnSuccess { saveToDatabase(it) }
            .doOnError { Log.d(REMOTE, it.message ?: it.toString()) }
            .onErrorResumeNext(getLocalNews())



    }


    fun getNewsWithCategory(category: String): Single<List<NewsItem>> {
        return remote.getFeed()

            .map {
                it.channel!!.items!!.filter { item -> item.category == category }
                    .map { item -> item.mapToNewsItem() }
            }
            .doOnSuccess { saveToDatabase(it) }
            .doOnError { Log.d(REMOTE, it.message ?: it.toString()) }
            .onErrorResumeNext(getLocalNewsWithCategory(category))
    }
}



