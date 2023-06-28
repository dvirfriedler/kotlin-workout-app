package com.example.myapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.myapplication.databinding.MenuItemBinding
import com.example.myapplication.data.entities.MenuItem


class MenuItemAdapter :
    ListAdapter<MenuItem, MenuItemAdapter.MenuItemViewHolder>(MenuItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val menuItem = getItem(position)
        holder.bindMenuItemData(menuItem)
    }

    inner class MenuItemViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindMenuItemData(menuItem: MenuItem) {
            with(binding) {

                recipeTextView.text = menuItem.recipe

                prepTimeInMinutesTextView.text = menuItem.prep_time_in_minutes.toString()

                cookTimeInMinutesTextView.text = menuItem.cook_time_in_minutes.toString()

                difficultyTextView.text = menuItem.difficulty

                caloriesTextView.text = menuItem.calories.toString()

                fatInGramsTextView.text = menuItem.fat_in_grams.toString()

                proteinInGramsTextView.text = menuItem.protein_in_grams.toString()

                val ingredientTextViews = arrayOf(
                    ingredient1TextView,
                    ingredient2TextView,
                    ingredient3TextView,
                    ingredient4TextView,
                    ingredient5TextView,
                    ingredient6TextView,
                    ingredient7TextView,
                    ingredient8TextView,
                    ingredient9TextView,
                    ingredient10TextView
                )

                for (i in 0 until ingredientTextViews.size) {
                    val textView = ingredientTextViews[i]
                    val ingredient = when (i) {
                        0 -> menuItem.ingredient_1
                        1 -> menuItem.ingredient_2
                        2 -> menuItem.ingredient_3
                        3 -> menuItem.ingredient_4
                        4 -> menuItem.ingredient_5
                        5 -> menuItem.ingredient_6
                        6 -> menuItem.ingredient_7
                        7 -> menuItem.ingredient_8
                        8 -> menuItem.ingredient_9
                        9 -> menuItem.ingredient_10
                        else -> null
                    }

                    val measurement = when (i) {
                        0 -> menuItem.measurement_1
                        1 -> menuItem.measurement_2
                        2 -> menuItem.measurement_3
                        3 -> menuItem.measurement_4
                        4 -> menuItem.measurement_5
                        5 -> menuItem.measurement_6
                        6 -> menuItem.measurement_7
                        7 -> menuItem.measurement_8
                        8 -> menuItem.measurement_9
                        9 -> menuItem.measurement_10
                        else -> null
                    }

                    textView.text = "$ingredient - $measurement"
                    textView.visibility = if (ingredient.isNullOrEmpty() || measurement.toString()
                            .isEmpty()
                    ) View.GONE else View.VISIBLE
                }


                val directionsTextViews = arrayOf(
                    directionsStep1TextView,
                    directionsStep2TextView,
                    directionsStep3TextView,
                    directionsStep4TextView,
                    directionsStep5TextView,
                    directionsStep6TextView,
                    directionsStep7TextView,
                    directionsStep8TextView,
                    directionsStep9TextView,
                    directionsStep10TextView
                )

                for (i in directionsTextViews.indices) {
                    val textView = directionsTextViews[i]
                    val directionStep = when (i) {
                        0 -> menuItem.directions_step_1
                        1 -> menuItem.directions_step_2
                        2 -> menuItem.directions_step_3
                        3 -> menuItem.directions_step_4
                        4 -> menuItem.directions_step_5
                        5 -> menuItem.directions_step_6
                        6 -> menuItem.directions_step_7
                        7 -> menuItem.directions_step_8
                        8 -> menuItem.directions_step_9
                        9 -> menuItem.directions_step_10
                        else -> null
                    }

                    textView.text = directionStep
                    textView.visibility =
                        if (directionStep.isNullOrEmpty()) View.GONE else View.VISIBLE
                }

                if (!menuItem.image.isNullOrEmpty()) {
                    Glide.with(root.context)
                        .load(menuItem.image)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)
                }

                executePendingBindings()
            }
        }
    }


    private class MenuItemDiffCallback : DiffUtil.ItemCallback<MenuItem>() {
        override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem == newItem
        }
    }
}


