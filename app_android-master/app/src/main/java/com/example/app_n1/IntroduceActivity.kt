package com.example.app_n1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app_n1.databinding.ActivityIntroduceBinding


class IntroduceActivity : AppCompatActivity() {

    private val binding: ActivityIntroduceBinding by lazy {
        ActivityIntroduceBinding.inflate(layoutInflater)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnMain.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
    }
}