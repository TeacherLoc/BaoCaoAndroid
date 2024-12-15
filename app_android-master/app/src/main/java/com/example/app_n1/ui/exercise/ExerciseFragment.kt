package com.example.app_n1.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_n1.Adapter.ExerciseAdapter
import com.example.app_n1.ExerciseDetailActivity
import com.example.app_n1.databinding.FragmentExerciseBinding
import com.example.app_n1.models.Exercise


class ExerciseFragment : Fragment() {

    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Danh sách bài tập mẫu
        val exercises = listOf(
            Exercise("Push-ups", "A basic push exercise for the upper body."),
            Exercise("Squats", "A fundamental lower-body exercise."),
            Exercise("Plank", "A core stabilization exercise."),
            Exercise("Lunges", "Great for balance and strengthening legs."),
            Exercise("Sit-ups", "Classic core exercise for abs."),
        )

        // Khởi tạo adapter và thiết lập RecyclerView
        val adapter = ExerciseAdapter(exercises) { exercise ->
            // Mở ExerciseDetailActivity khi nhấn vào item
            val intent = Intent(requireContext(), ExerciseDetailActivity::class.java).apply {
                putExtra("exercise_title", exercise.title)
                putExtra("exercise_description", exercise.description)
            }
            startActivity(intent)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
