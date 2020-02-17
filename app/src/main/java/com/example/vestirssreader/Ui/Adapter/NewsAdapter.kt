package com.example.vestirssreader.Ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.vestirssreader.Data.Local.Item
import com.example.vestirssreader.Data.Local.NewsItem
import com.example.vestirssreader.R

import java.util.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

   private val mNewsList: MutableList<NewsItem> = ArrayList()

    private var mCallback: BaseAdapterCallback? = null

    fun attachCallback(callback: BaseAdapterCallback) {
        this.mCallback = callback
    }

    fun detachCallback() {
        this.mCallback = null
    }

    fun setData(news:List<NewsItem>) {
        mNewsList.clear()
        mNewsList.addAll(news)

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
    }

    override fun getItemCount(): Int =  mNewsList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mNewsList[position])
        holder.itemView.setOnClickListener {
            mCallback?.onItemClick(mNewsList[position])
        }
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val newsTitle:TextView = itemView.findViewById(R.id.title_item)
        private val newsDate:TextView = itemView.findViewById(R.id.date_item)

        fun bind(news:NewsItem) {
            newsTitle.text = news.title
            newsDate.text = news.pubDate
        }
    }
}