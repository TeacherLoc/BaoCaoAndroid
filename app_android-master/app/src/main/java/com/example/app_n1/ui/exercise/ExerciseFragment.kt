package com.example.app_n1.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_n1.Adapter.ExerciseAdapter
import com.example.app_n1.R
import com.example.app_n1.databinding.FragmentExerciseBinding
import com.example.app_n1.models.Exercise


class ExerciseFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExerciseAdapter
    private val exerciseList = listOf(
        Exercise("Hít đất", "Cách tập: Đẩy lên và xuống ...", R.drawable.user_image),
        Exercise("Gập bụng", "Cách tập: Nằm ngửa, co chân ...", R.drawable.user_image),
        Exercise("Chạy bộ", "Cách tập: Chạy nhẹ nhàng ...", R.drawable.user_image)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = ExerciseAdapter(exerciseList) { exercise ->
            val action = ExerciseFragmentDirections
                .actionNavigationExerciseToExerciseDetailFragment(
                    exercise.title,
                    exercise.description,
                    exercise.imageResId
                )
            findNavController().navigate(action)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        return view
    }
}
