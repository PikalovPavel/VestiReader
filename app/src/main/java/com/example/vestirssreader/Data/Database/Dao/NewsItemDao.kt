package com.example.vestirssreader.Data.Database.Dao

import androidx.room.*
import com.example.vestirssreader.Data.Database.Enity.NewsItem
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface NewsItemDao {

    @Query("SELECT * FROM newsitem ORDER by date DESC LIMIT 70")
    fun getNews(): Flowable<List<NewsItem>>

    @Query("SELECT * FROM newsitem WHERE category=:category ORDER by date DESC LIMIT 70")
    fun getCategoryNews(category:String): Flowable<List<NewsItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllNews(news: List<NewsItem>)



}