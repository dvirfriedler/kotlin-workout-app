package com.example.myapplication.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.entities.YourWorkout

@Dao
interface YourWorkoutDao {
    @Query("SELECT * FROM yourWorkouts")
    fun getAllYourWorkouts(): LiveData<List<YourWorkout>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateYourWorkout(workout: YourWorkout)

    @Query("DELETE FROM yourWorkouts WHERE name = :name")
    fun deleteYourWorkoutByName(name: String)
}