package com.example.vestirssreader.Ui.AllNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vestirssreader.Data.Repository.NewsRepository

@Suppress("UNCHECKED_CAST")
class AllNewsModelFactory(
    private val repository: NewsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AllNewsViewModel(repository) as T
    }

}