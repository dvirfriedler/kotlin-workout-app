package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.dao.WorkoutDao
import com.example.myapplication.data.entities.Workout
import com.example.myapplication.data.services.WorkoutApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val workoutApiService: WorkoutApiService
) {
    val workouts: LiveData<List<Workout>> = workoutDao.getAllWorkouts()

    suspend fun getExerciseList(): List<Workout>? {
        try {

            val response = withContext(Dispatchers.IO) {
                workoutApiService.getExerciseList().execute()
            }

            if (response.isSuccessful) {
                println(response.body())
                return response.body()

            } else {
                // Handle unsuccessful response
                throw Exception("Failed to fetch exercise list")
            }

        } catch (e: Exception) {
            throw Exception("Failed to fetch exercise list", e)
        }
    }

    suspend fun insertOrUpdateWorkout(workout: Workout) {
        withContext(Dispatchers.IO) {
            workoutDao.insertOrUpdateWorkout(workout)
        }
    }

    suspend fun insertOrUpdateWorkoutList(workouts: List<Workout>) {
        withContext(Dispatchers.IO) {
            workoutDao.insertOrUpdateWorkoutList(workouts)
        }
    }

    suspend fun deleteWorkout(workoutName: String) {
        withContext(Dispatchers.IO) {
            workoutDao.deleteWorkoutByName(workoutName)
        }
    }
}
