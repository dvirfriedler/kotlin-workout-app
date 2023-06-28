package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.dao.YourWorkoutDao
import com.example.myapplication.data.entities.YourWorkout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class YourWorkoutsRepository @Inject constructor(private val yourWorkoutDao: YourWorkoutDao) {
    val yourWorkouts: LiveData<List<YourWorkout>> = yourWorkoutDao.getAllYourWorkouts()

    suspend fun insertOrUpdateYourWorkout(workout: YourWorkout) {
        withContext(Dispatchers.IO) {
            yourWorkoutDao.insertOrUpdateYourWorkout(workout)
        }
    }

    suspend fun deleteYourWorkout(workout: YourWorkout) {
        withContext(Dispatchers.IO) {
            yourWorkoutDao.deleteYourWorkoutByName(workout.name)
        }
    }

    suspend fun getAllYourWorkouts() {
        withContext(Dispatchers.IO) {
            yourWorkoutDao.getAllYourWorkouts()
        }
    }
}
