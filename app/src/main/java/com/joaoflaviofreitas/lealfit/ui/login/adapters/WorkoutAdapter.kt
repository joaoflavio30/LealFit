package com.joaoflaviofreitas.lealfit.ui.login.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joaoflaviofreitas.lealfit.databinding.WorkoutItemBinding
import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Workout


class WorkoutAdapter(private val clickItem: (Workout) -> Unit) :
    ListAdapter<Workout, WorkoutAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: WorkoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: Workout) {
            binding.trainingTitle.text = workout.name
            binding.date.text = workout.date
            binding.description.text = workout.description
            binding.root.setOnClickListener {
                clickItem(workout)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutAdapter.ViewHolder =
        ViewHolder(WorkoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: WorkoutAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Workout>() {
        override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem == newItem
        }
    }
}