package com.example.app_n1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app_n1.databinding.ActivityExerciseDetailBinding

class ExerciseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nhận dữ liệu từ Intent
        val title = intent.getStringExtra("exercise_title")
        val description = intent.getStringExtra("exercise_description")

        // Hiển thị dữ liệu
        binding.tvTitle.text = title
        binding.tvDescription.text = description
    }
}
