package com.example.myapplication.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.entities.Workout

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts WHERE name NOT IN (SELECT name FROM yourWorkouts)")
    fun getAllWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workouts WHERE id = :id")
    fun getWorkoutById(id: Int): LiveData<Workout?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateWorkout(workout: Workout)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateWorkoutList(workouts: List<Workout>)

    @Query("DELETE FROM workouts WHERE name = :name")
    fun deleteWorkoutByName(name: String)
}