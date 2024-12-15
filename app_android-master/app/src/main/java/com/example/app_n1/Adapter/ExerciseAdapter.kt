package com.example.app_n1.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_n1.R

data class Exercise(val title: String, val description: String)

class ExerciseAdapter(
    private val exercises: List<com.example.app_n1.models.Exercise>,
    private val onItemClick: (Exercise) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_exercise_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_exercise_description)

        fun bind(exercise: com.example.app_n1.models.Exercise) {
            tvTitle.text = exercise.title
            tvDescription.text = exercise.description
            itemView.setOnClickListener { onItemClick(exercise) }
        }
    }

    private fun onItemClick(exercise: com.example.app_n1.models.Exercise) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_item, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override fun getItemCount() = exercises.size
}
