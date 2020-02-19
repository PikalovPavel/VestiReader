package com.example.vestirssreader.Data.Remote

import com.example.vestirssreader.Data.Remote.Response.Rss
import io.reactivex.Single
import retrofit2.http.GET

interface NewsApi {
    @GET("vesti.rss")
        fun getFeed(): Single<Rss>
}