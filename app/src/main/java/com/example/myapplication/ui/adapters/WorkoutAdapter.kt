package com.example.myapplication.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myapplication.R
import com.example.myapplication.databinding.WorkoutItemBinding
import com.example.myapplication.data.entities.Workout

class WorkoutAdapter :
    ListAdapter<Workout, WorkoutAdapter.WorkoutViewHolder>(WorkoutDiffCallback()) {


    private var onItemClickListener: ((Workout) -> Unit)? = null

    fun setOnItemClickListener(listener: (Workout) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = WorkoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = getItem(position)
        holder.bindWorkoutData(workout)
        holder.addButtonClickListener(workout)
    }

    inner class WorkoutViewHolder(private val binding: WorkoutItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindWorkoutData(workout: Workout) {
            with(binding) {
                nameTextView.text = workout.name
                bodyPartTextView.text = workout.bodyPart
                targetTextView.text = workout.target
                equipmentTextView.text = workout.equipment

                if (workout.gifUrl.isNotEmpty()) {
                    Glide.with(root.context)
                        .asGif()
                        .load(workout.gifUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)
                }

                executePendingBindings()
            }
        }

        fun addButtonClickListener(workout: Workout) {
            val button = createButton()
            setButtonClickListener(button, workout)
            addButtonToContainer(button)
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        private fun createButton(): ImageButton {
            val button = ImageButton(binding.root.context)
            val iconResId = R.drawable.add_icon
            val icon = binding.root.resources.getDrawable(iconResId)
            button.setImageDrawable(icon)
            val layoutParams = createButtonLayoutParams()
            button.layoutParams = layoutParams
            return button
        }

        private fun createButtonLayoutParams(): ViewGroup.MarginLayoutParams {
            val layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            layoutParams.setMargins(16, 0, 16, 0)
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            return layoutParams
        }

        private fun setButtonClickListener(button: ImageButton, workout: Workout) {
            button.setOnClickListener {

                showAddItemDialog(binding.root, workout.name) { isOkClicked ->
                    if (isOkClicked) {
                        onItemClickListener?.invoke(workout)
                    }
                }
            }
        }

        private fun addButtonToContainer(button: ImageButton) {
            val container = binding.root as? ViewGroup
            container?.addView(button)
        }
    }

    private fun showAddItemDialog(
        view: View,
        ex_name: String,
        callback: (Boolean) -> Unit
    ) {
        val context = view.context
        val builder = AlertDialog.Builder(context)
        val title = context.getString(R.string.ex_add)

        val toastMessage = "$ex_name ${context.getString(R.string.is_added)}"
        builder.setTitle(title)

        builder.setMessage("$title $ex_name?")

        builder.setPositiveButton(R.string.ok) { dialog, _ ->
            // Handle OK button click
            callback(true)
            dialog.dismiss()
            Toast.makeText(view.context, toastMessage, Toast.LENGTH_SHORT).show()

        }

        builder.setNegativeButton(R.string.cancel) { dialog, _ ->
            // Handle Cancel button click
            callback(false)
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }


    private class WorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {
        override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem == newItem
        }
    }
}


