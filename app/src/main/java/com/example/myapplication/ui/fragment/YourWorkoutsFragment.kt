package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.adapters.YourWorkoutAdapter
import com.example.myapplication.databinding.YourWorkoutsFragmentBinding
import com.example.myapplication.data.entities.Workout
import com.example.myapplication.data.entities.YourWorkout
import com.example.myapplication.ui.viewmodel.WorkoutViewModel
import com.example.myapplication.ui.viewmodel.YourWorkoutsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YourWorkoutsFragment : Fragment() {
    private lateinit var binding: YourWorkoutsFragmentBinding
    private val workoutViewModel: WorkoutViewModel by viewModels()
    private val yourWorkoutsViewModel: YourWorkoutsViewModel by viewModels()
    private lateinit var adapter: YourWorkoutAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = YourWorkoutsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView(view)
        observeWorkoutItems()
    }

    private fun initializeRecyclerView(view: View) {
        adapter = YourWorkoutAdapter()
        recyclerView = view.findViewById(R.id.your_workout_list)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { yourWorkout ->
            onItemClick(yourWorkout, yourWorkoutsViewModel, workoutViewModel)
        }
    }


    private fun observeWorkoutItems() {
        yourWorkoutsViewModel.yourWorkouts.observe(viewLifecycleOwner) { yourWorkouts: List<YourWorkout> ->
            if (yourWorkouts.isEmpty()) {
                // Show empty state
                binding.yourWorkoutList.visibility = View.GONE
                val emptyStateView = LayoutInflater.from(context)
                    .inflate(R.layout.empty_state_layout, binding.yourWorkoutList, false)
                (binding.root as ViewGroup).addView(emptyStateView)
            } else {
                // Hide empty state
                binding.yourWorkoutList.visibility = View.VISIBLE
                adapter.submitList(yourWorkouts)
            }
        }
    }

    private fun onItemClick(
        yourWorkout: YourWorkout,
        yourWorkoutsViewModel: YourWorkoutsViewModel,
        workoutViewModel: WorkoutViewModel
    ) {
        yourWorkoutsViewModel.deleteYourWorkout(yourWorkout)

        val workout = Workout(
            id = yourWorkout.id,
            name = yourWorkout.name,
            bodyPart = yourWorkout.bodyPart,
            target = yourWorkout.target,
            gifUrl = yourWorkout.gifUrl,
            equipment = yourWorkout.equipment
        )

        workoutViewModel.insertOrUpdateWorkout(workout)
    }
}
