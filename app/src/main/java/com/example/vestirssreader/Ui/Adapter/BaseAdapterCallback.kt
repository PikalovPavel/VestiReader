package com.example.vestirssreader.Ui.Adapter

import com.example.vestirssreader.Data.Database.Enity.NewsItem

interface BaseAdapterCallback {
    fun onItemClick(model: NewsItem)
}