package com.example.myapplication.data.appDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.dao.MenuItemDao
import com.example.myapplication.data.dao.WorkoutDao
import com.example.myapplication.data.dao.YourWorkoutDao
import com.example.myapplication.data.entities.MenuItem
import com.example.myapplication.data.entities.Workout
import com.example.myapplication.data.entities.YourWorkout

@Database(entities = [Workout::class, MenuItem::class, YourWorkout::class], version = 25, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun yourWorkoutDao(): YourWorkoutDao
    abstract fun menuItemDao(): MenuItemDao
}


