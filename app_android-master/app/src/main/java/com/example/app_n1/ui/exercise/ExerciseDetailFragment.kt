package com.example.app_n1.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.exercise_detail_fragment, container, false)

        val args = ExerciseDetailFragmentArgs.fromBundle(requireArguments())
        val imageView: ImageView = view.findViewById(R.id.imageExerciseDetail)
        val titleView: TextView = view.findViewById(R.id.textExerciseTitleDetail)
        val descriptionView: TextView = view.findViewById(R.id.textExerciseDescription)

        imageView.setImageResource(args.imageResId)
        titleView.text = args.title
        descriptionView.text = args.description
        return view
    }
}

