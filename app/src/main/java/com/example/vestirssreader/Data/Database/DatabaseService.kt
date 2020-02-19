package com.example.vestirssreader.Data.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vestirssreader.Data.Database.Dao.NewsItemDao
import com.example.vestirssreader.Data.Database.Enity.NewsItem

@Database(entities = [NewsItem::class], version = 1)
abstract class DatabaseService : RoomDatabase() {

    abstract fun getNewsDao(): NewsItemDao


    companion object {
        @Volatile
        private var INSTANCE: DatabaseService? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        fun getInstance(context: Context): DatabaseService =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext, DatabaseService::class.java,
                "News.db"
            )
                .build()

    }
}
