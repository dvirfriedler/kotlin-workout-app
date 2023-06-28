package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.entities.Workout
import com.example.myapplication.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class WorkoutViewModel  @Inject constructor(
    private val repository: WorkoutRepository,
) : ViewModel() {
    val workouts: LiveData<List<Workout>> = repository.workouts
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage



    fun fetchWorkouts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val exerciseList = repository.getExerciseList()

                if (exerciseList != null) {
                    insertOrUpdateWorkoutList(exerciseList)
                }

            } catch (e: Exception) {
                // Log the exception
                Log.e("WorkoutViewModel", "Failed to fetch workouts", e)
                // If you want to display an error message to the user, you can post a value to a LiveData here
                _errorMessage.postValue("Failed to fetch workouts due to network issues.")
            }
        }
    }

    private suspend fun insertOrUpdateWorkoutList(workouts: List<Workout>) {
        withContext(Dispatchers.IO) {
            repository.insertOrUpdateWorkoutList(workouts)
        }
    }

    fun insertOrUpdateWorkout(workout: Workout) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.insertOrUpdateWorkout(workout)
        }
    }

    fun deleteWorkout(workoutName: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.deleteWorkout(workoutName)
        }
    }
}
