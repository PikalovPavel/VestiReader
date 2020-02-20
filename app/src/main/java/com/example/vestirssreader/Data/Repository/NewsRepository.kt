package com.example.vestirssreader.Data.Repository


import com.example.vestirssreader.Data.Database.Dao.NewsItemDao

import com.example.vestirssreader.Data.Database.Enity.NewsItem
import com.example.vestirssreader.Data.Remote.NewsApi
import com.example.vestirssreader.Util.isNetworkAvailable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers




class NewsRepository (private val remote: NewsApi,
                      private val local: NewsItemDao) {

    fun getNews(): Flowable<List<NewsItem>> {

        return if (isNetworkAvailable()) {
        return remote.getFeed()
                .toFlowable()
                .map { rss ->
                    rss.channel?.items?.map { item ->
                        item.mapToNewsItem()
                    }
                }
                .onErrorResumeNext(getLocalData())
                .doOnNext{ items -> saveToDatabase(items) }
                .flatMap{items -> Flowable.just(items) }


         }
        else {
            getLocalData()
        }

    }

    fun getNewsWithCategory(category:String):Flowable<List<NewsItem>> {
        return if (isNetworkAvailable()) {
             remote.getFeed()
                    .toFlowable()
                    .map {it.channel?.items?.filter { item -> item.category == category }?.map { item -> item.mapToNewsItem()}}
                    .onErrorResumeNext(getLocalNewsWithCategory(category))
                    .doOnNext{ items ->
                        saveToDatabase(items)
                    }
                    .flatMap { Flowable.just(it) }

            }
        else {
            getLocalNewsWithCategory(category)
        }

    }

    private fun saveToDatabase(news:List<NewsItem>?) {
        if (!news.isNullOrEmpty())
        Observable.fromCallable {
            local.saveAllNews(news)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()


    }

    private fun getLocalData():Flowable<List<NewsItem>> {
       return local.getNews()
    }
    private fun getLocalNewsWithCategory(category: String): Flowable<List<NewsItem>> {
        return local.getCategoryNews(category)
    }





}