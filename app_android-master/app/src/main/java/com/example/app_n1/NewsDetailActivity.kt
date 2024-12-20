package com.example.app_n1

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        // Lấy dữ liệu được truyền từ Intent
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val imageRes = intent.getIntExtra("imageRes", 0)

        // Gán dữ liệu cho các View
        val titleView: TextView = findViewById(R.id.newsDetailTitle)
        val descriptionView: TextView = findViewById(R.id.newsDetailDescription)
        val imageView: ImageView = findViewById(R.id.newsDetailImage)

        titleView.text = title
        descriptionView.text = description
        imageView.setImageResource(imageRes)
    }
}
