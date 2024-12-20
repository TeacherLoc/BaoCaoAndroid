package com.example.app_n1.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_n1.R
import com.example.app_n1.models.NewsItem

class NewsAdapter(
    private val newsList: List<NewsItem>,
    private val onItemClick: (NewsItem) -> Unit // Callback cho sự kiện nhấn
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.title.text = newsItem.title
        holder.description.text = newsItem.description
        holder.image.setImageResource(newsItem.imageRes)

        // Xử lý sự kiện nhấn vào item
        holder.itemView.setOnClickListener {
            onItemClick(newsItem)
        }
    }

    override fun getItemCount(): Int = newsList.size

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.newsImage)
        val title: TextView = view.findViewById(R.id.newsTitle)
        val description: TextView = view.findViewById(R.id.newsDescription)
    }
}
