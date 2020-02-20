package com.example.vestirssreader

import android.app.Application
import com.example.vestirssreader.Data.Database.Dao.NewsItemDao
import com.example.vestirssreader.Data.Database.DatabaseService
import com.example.vestirssreader.Data.Remote.NewsApiService
import com.example.vestirssreader.Data.Repository.NewsRepository
import com.example.vestirssreader.Ui.AllNews.AllNewsModelFactory
import com.example.vestirssreader.Ui.DetailNews.DetailNewsViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class VestiApplication:Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@VestiApplication))

        bind<NewsApiService>() with singleton {
            NewsApiService.create()
        }

        bind<DatabaseService>() with singleton {
            DatabaseService.getInstance(applicationContext)
        }
        bind<NewsItemDao>() with singleton {
            instance<DatabaseService>().getNewsDao()
        }
        bind<NewsRepository>() with singleton {
            NewsRepository(instance(), instance())

        }
        bind() from provider { DetailNewsViewModelFactory(instance()) }
        bind() from provider { AllNewsModelFactory(instance())}
    }
}