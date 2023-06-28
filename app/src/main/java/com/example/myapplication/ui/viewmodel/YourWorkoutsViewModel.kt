package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.entities.YourWorkout
import com.example.myapplication.data.repository.YourWorkoutsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class YourWorkoutsViewModel @Inject constructor(private val repository: YourWorkoutsRepository) : ViewModel() {
    val yourWorkouts: LiveData<List<YourWorkout>> = repository.yourWorkouts

    fun insertOrUpdateYourWorkout(workout: YourWorkout) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.insertOrUpdateYourWorkout(workout)
        }
    }

    fun deleteYourWorkout(workout: YourWorkout) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.deleteYourWorkout(workout)
        }
    }
}