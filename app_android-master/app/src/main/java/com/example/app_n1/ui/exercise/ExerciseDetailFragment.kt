package com.example.app_n1.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_n1.Adapter.ExerciseAdapter
import com.example.app_n1.R
import com.example.app_n1.databinding.FragmentExerciseBinding
import com.example.app_n1.models.Exercise

class ExerciseDetailFragment : Fragment() {

    private lateinit var textTimer: TextView
    private lateinit var btnPause: Button
    private lateinit var btnResume: Button
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 30000 // 30 giây
    private var isTimerRunning = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.exercise_detail_fragment, container, false)

        textTimer = view.findViewById(R.id.textTimer)
        btnPause = view.findViewById(R.id.btnPause)
        btnResume = view.findViewById(R.id.btnResume)

        updateTimerText()

        btnPause.setOnClickListener {
            pauseTimer()
        }

        btnResume.setOnClickListener {
            resumeTimer()
        }

        // lay thong tin chi tiet bai tap
        val args = ExerciseDetailFragmentArgs.fromBundle(requireArguments())
        val imageView: ImageView = view.findViewById(R.id.imageExerciseDetail)
        val titleView: TextView = view.findViewById(R.id.textExerciseTitleDetail)
        val descriptionView: TextView = view.findViewById(R.id.textExerciseDescription)

        imageView.setImageResource(args.imageResId)
        titleView.text = args.title
        descriptionView.text = args.description

        return view
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                textTimer.text = "Done!"
                isTimerRunning = false
            }
        }.start()
        isTimerRunning = true
    }

    private fun pauseTimer() {
        if (isTimerRunning) {
            countDownTimer?.cancel()
            isTimerRunning = false
        }
    }

    private fun resumeTimer() {
        if (!isTimerRunning) {
            // Khởi động đồng hồ chỉ khi nhấn Resume
            startTimer()
        }
    }

    private fun updateTimerText() {
        val secondsLeft = timeLeftInMillis / 1000
        textTimer.text = secondsLeft.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }
}

