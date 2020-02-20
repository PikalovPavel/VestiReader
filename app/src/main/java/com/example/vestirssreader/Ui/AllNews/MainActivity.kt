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
import com.example.vestirssreader.Data.AnswerState
import com.example.vestirssreader.Data.Database.Enity.NewsItem
import com.example.vestirssreader.R
import com.example.vestirssreader.Ui.Adapter.BaseAdapterCallback
import com.example.vestirssreader.Ui.Adapter.NewsAdapter
import com.example.vestirssreader.Ui.DetailNews.DetailNewActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import android.view.KeyEvent.KEYCODE_DPAD_CENTER
import android.view.MotionEvent


class MainActivity : AppCompatActivity(),KodeinAware {
    private val TAG = "fetchDataError"
    private val factory : AllNewsModelFactory by instance()

    override val kodein by kodein()
    private lateinit var viewModel: AllNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, factory).get(AllNewsViewModel::class.java)
        initSpinner()
        val newsAdaper = NewsAdapter()

        swipeRefresh.setOnRefreshListener {
            getNews(categoriesSpinner.selectedItem.toString())
        }

        viewModel.news.observe(this, Observer { items ->
            newsAdaper.setData(items)
        })

        viewModel.answerState.observe(this, Observer {
                when (it.first) {
                    AnswerState.FAILURE -> {
                        hideLoading()
                        fetch_data_info.text = resources.getString(R.string.fetch_data_trouble)
                        go_wrong_sign.visibility = View.VISIBLE
                        fetch_data_info.visibility = View.VISIBLE
                        Log.d(TAG, it.second)
                    }
                    AnswerState.SUCCESS -> {
                        hideLoading()
                       if (it.second == "true") {
                           fetch_data_info.text = resources.getString(R.string.fetch_data_empty)
                           fetch_data_info.visibility = View.VISIBLE
                       } else fetch_data_info.visibility = View.GONE

                    }
                    AnswerState.LOADING ->  {
                        if (!swipeRefresh.isRefreshing)
                            showLoading()
                    }

                }
        })

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

    private fun hideLoading() {
        progressBarAllNews.visibility = View.GONE
        go_wrong_sign.visibility = View.GONE
        swipeRefresh.isRefreshing = false

    }
    private fun showLoading() {
        progressBarAllNews.visibility = View.VISIBLE
    }


    private fun initSpinner() {

        categoriesSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!viewModel.rotation.value!!) {
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    getNews(selectedItem)
                    viewModel.rotation.value = false
                }

            }

        }

        val spinnerOnTouch = View.OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                viewModel.rotation.value = false
            }
            false
        }
        val spinnerOnKey = View.OnKeyListener { _, keyCode, _ ->
            viewModel.rotation.value = false
            keyCode == KEYCODE_DPAD_CENTER
        }

        categoriesSpinner.setOnTouchListener(spinnerOnTouch)
        categoriesSpinner.setOnKeyListener(spinnerOnKey)


    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.rotation.value = true
    }


    private fun getNews(category: String) {
        if (category=="Все") viewModel.getNews() else
            viewModel.getNewsWithCategory(category)
    }





}
