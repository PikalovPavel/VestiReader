package com.example.vestirssreader.Ui.AllNews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vestirssreader.Data.Local.NewsItem
import com.example.vestirssreader.R
import com.example.vestirssreader.Ui.Adapter.BaseAdapterCallback
import com.example.vestirssreader.Ui.Adapter.NewsAdapter
import com.example.vestirssreader.Ui.DetailNews.DetailNewActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(),KodeinAware {

    private val factory : AllNewsModelFactory by instance()

    override val kodein by kodein()
    private lateinit var viewModel: AllNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, factory).get(AllNewsViewModel::class.java)

        val newsAdaper = NewsAdapter()
        viewModel.news.observe(this, Observer { items ->
            newsAdaper.setData(items)
        })

        viewModel.networkState.observe(this, Observer {

        })
        categoriesSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                if (selectedItem=="Все") viewModel.getNews() else
                viewModel.getNewsWithCategory(selectedItem)
            }

        }
        newsAdaper.attachCallback(object : BaseAdapterCallback{
            override fun onItemClick(model: NewsItem) {
                val intent = Intent(applicationContext, DetailNewActivity::class.java)
                intent.putExtra("news", model)
                startActivity(intent)
            }

        })
        all_news_recycler_view.adapter = newsAdaper
        all_news_recycler_view.layoutManager = LinearLayoutManager(this)

    }




}
