package com.example.vestirssreader.Data.Database.Dao

import androidx.room.*
import com.example.vestirssreader.Data.Database.Enity.NewsItem
import io.reactivex.Flowable

@Dao
interface NewsItemDao {

    @Query("SELECT * FROM newsitem ORDER by date LIMIT 50")
    fun getNews(): Flowable<List<NewsItem>>

    @Query("SELECT * FROM newsitem WHERE category=:category ORDER by date LIMIT 50")
    fun getCategoryNews(category:String): Flowable<List<NewsItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllNews(news: List<NewsItem>)

    @Query("DELETE FROM newsitem")
    fun deleteNews()

}