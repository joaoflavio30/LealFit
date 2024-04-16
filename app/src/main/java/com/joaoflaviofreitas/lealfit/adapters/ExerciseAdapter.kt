package com.joaoflaviofreitas.lealfit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.joaoflaviofreitas.lealfit.databinding.ExerciseItemBinding
import com.joaoflaviofreitas.lealfit.domain.model.Exercise


class ExerciseAdapter(private val clickItem: (Exercise) -> Unit) :
    ListAdapter<Exercise, ExerciseAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ExerciseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.exerciseTitle.text = exercise.name
            binding.observations.text = exercise.observations
            Glide.with(binding.root.context)
                .load(exercise.image).diskCacheStrategy(
                    DiskCacheStrategy.ALL,
                ).transition(
                    DrawableTransitionOptions.withCrossFade(),
                ).into(binding.image)
            binding.root.setOnClickListener {
                clickItem(exercise)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ExerciseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem == newItem
        }
    }
}