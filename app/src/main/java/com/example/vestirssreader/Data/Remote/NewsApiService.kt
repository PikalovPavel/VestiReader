package com.example.vestirssreader.Data.Remote

import com.example.vestirssreader.Data.Remote.Response.Rss
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET

interface  NewsApiService{

    @GET("vesti.rss")
    fun getFeed(): Single<Rss>

    companion object
    {
        const val BASE_URL: String = "https://www.vesti.ru/"
        fun create(): NewsApiService = createApi(HttpUrl.parse(BASE_URL)!!)

        private fun createApi(httpUrl: HttpUrl): NewsApiService {

            val okHttpClient = OkHttpClient.Builder().build()

            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(okHttpClient)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NewsApiService::class.java)
        }
    }
}