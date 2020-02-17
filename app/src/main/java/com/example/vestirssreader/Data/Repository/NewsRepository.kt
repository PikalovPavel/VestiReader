package com.example.vestirssreader.Data.Repository

import com.example.vestirssreader.Data.Local.Item
import com.example.vestirssreader.Data.Local.NewsItem
import com.example.vestirssreader.Data.Remote.NewsApi
import io.reactivex.Observable
import io.reactivex.Single

class NewsRepository (private val apiService: NewsApi) {

    fun getNews(): Single<List<NewsItem>> {
        return apiService.getFeed().map {
                rss -> rss.channel?.items?.map {item ->
                item.mapToNewsItem()}}
    }

    fun getNewsWithCategory(category:String):Single<List<NewsItem>> {
        return apiService.getFeed()
            .map {it.channel?.items?.filter {
                    item -> item.category == category }?.map {
                    item -> item.mapToNewsItem()}}

    }





}