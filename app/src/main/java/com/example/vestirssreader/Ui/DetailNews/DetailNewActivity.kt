package com.example.vestirssreader.Ui.DetailNews

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.vestirssreader.R
import kotlinx.android.synthetic.main.activity_detail_news.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.vestirssreader.Data.Database.Enity.NewsItem
import com.example.vestirssreader.Util.removeLines


class DetailNewActivity : AppCompatActivity(), KodeinAware {
    private val TAG = "GLIDE"
    private val factory : DetailNewsViewModelFactory by instance()

    override val kodein by kodein()
    private lateinit var viewModel: DetailNewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)
        viewModel = ViewModelProvider(this, factory).get(DetailNewsViewModel::class.java)
        back_button.setOnClickListener {
            onBackPressed()
        }
        val news = intent.getParcelableExtra<NewsItem>("news")

        if (news==null) {
            onBackPressed()
        }
            else attachOnUi(news)

    }


    private fun attachOnUi(news: NewsItem) {
        news_title.text = news.title
        time_news.text = news.printDate()
        full_text_news.text = removeLines(news.fullText)
        Glide.with(this)
            .load(news.link)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar_cover.visibility = View.VISIBLE
                    Log.d(TAG, e!!.message?:e.toString())
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar_cover.visibility = View.INVISIBLE
                    return false
                }
            })
            .into(news_cover)

    }


}
