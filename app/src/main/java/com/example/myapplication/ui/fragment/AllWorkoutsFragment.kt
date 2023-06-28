package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.adapters.WorkoutAdapter
import com.example.myapplication.databinding.AllWorkoutsFragmentBinding
import com.example.myapplication.data.entities.Workout
import com.example.myapplication.data.entities.YourWorkout
import com.example.myapplication.ui.viewmodel.WorkoutViewModel
import com.example.myapplication.ui.viewmodel.YourWorkoutsViewModel
import com.example.myapplication.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllWorkoutsFragment : Fragment() {
    private var binding: AllWorkoutsFragmentBinding by autoCleared()
    private val workoutViewModel: WorkoutViewModel by viewModels()
    private val yourWorkoutsViewModel: YourWorkoutsViewModel by viewModels()
    private lateinit var adapter: WorkoutAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AllWorkoutsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
        observeWorkoutItems()
        fetchWorkouts()
    }

    private fun initializeRecyclerView() {
        adapter = WorkoutAdapter()
        recyclerView = binding.yourWorkoutList
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { workout ->
            onItemClick(workout, workoutViewModel, yourWorkoutsViewModel)
        }
    }

    private fun observeWorkoutItems() {
        workoutViewModel.workouts.observe(viewLifecycleOwner) { workouts ->
            adapter.submitList(workouts)
        }

        workoutViewModel.errorMessage.observe(viewLifecycleOwner) { error: String? ->
            error.let { Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show() }
        }
    }

    private fun fetchWorkouts() {
        workoutViewModel.fetchWorkouts()
    }

    private fun onItemClick(
        workout: Workout,
        workoutViewModel: WorkoutViewModel,
        yourWorkoutsViewModel: YourWorkoutsViewModel
    ) {
        workoutViewModel.deleteWorkout(workout.name)

        val yourWorkout = YourWorkout(
            id = workout.id,
            name = workout.name,
            bodyPart = workout.bodyPart,
            target = workout.target,
            gifUrl = workout.gifUrl,
            equipment = workout.equipment
        )

        yourWorkoutsViewModel.insertOrUpdateYourWorkout(yourWorkout)
    }
}
