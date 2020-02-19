package com.example.vestirssreader.Data.Repository

import android.util.Log
import com.example.vestirssreader.Data.Database.Dao.NewsItemDao
import com.example.vestirssreader.Data.Database.DatabaseService
import com.example.vestirssreader.Data.Database.Enity.NewsItem
import com.example.vestirssreader.Data.Remote.NewsApi
import com.example.vestirssreader.Util.NetworkManager
import io.reactivex.Flowable
import io.reactivex.Single
import org.intellij.lang.annotations.Flow

class NewsRepository (private val remote: NewsApi,
                      private val local: NewsItemDao,
                      private val networkManager: NetworkManager) {

    fun getNews(): Flowable<List<NewsItem>> {
        return if (networkManager.isConnected())
            remote.getFeed()
                .map { rss -> rss.channel?.items?.map {item ->
                    item.mapToNewsItem()}
                }
                .doOnSuccess{Log.d("kek", "times" +
                        "")
                    saveToDatabase(it?: listOf())}
                .toFlowable()
                .onErrorResumeNext(getLocalData())
                .flatMap { Flowable.just(it) }


        else {
            getLocalData()
        }

    }

    fun getNewsWithCategory(category:String):Flowable<List<NewsItem>> {
        return if (networkManager.isConnected()) remote.getFeed()
            .map {it.channel?.items?.filter {
                    item -> item.category == category }?.map {
                    item -> item.mapToNewsItem()}}
            .doOnSuccess{Log.d("kek", "times" +
                    "")
                saveToDatabase(it?: listOf())}
            .toFlowable()
            .onErrorResumeNext( getLocalNewsWithCategory(category))
            .flatMap { Flowable.just(it) }
        else {
            getLocalNewsWithCategory(category)
        }

    }

    private fun saveToDatabase(news:List<NewsItem>) {
        local.saveAllNews(news)
    }

    private fun getLocalData():Flowable<List<NewsItem>> {
       return local.getNews()
    }
    private fun getLocalNewsWithCategory(category: String):Flowable<List<NewsItem>> {
        return local.getCategoryNews(category)
    }





}