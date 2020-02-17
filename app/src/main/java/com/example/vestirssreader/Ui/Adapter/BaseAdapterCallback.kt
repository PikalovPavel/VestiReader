package com.example.vestirssreader.Ui.Adapter

import android.view.View
import com.example.vestirssreader.Data.Local.NewsItem

interface BaseAdapterCallback {
    fun onItemClick(model: NewsItem)
}