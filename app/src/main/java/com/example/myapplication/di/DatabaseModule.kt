package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.appDatabase.AppDatabase
import com.example.myapplication.data.dao.MenuItemDao
import com.example.myapplication.data.dao.WorkoutDao
import com.example.myapplication.data.dao.YourWorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "workout_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideWorkoutDao(database: AppDatabase): WorkoutDao {
        return database.workoutDao()
    }

    @Provides
    fun provideYourWorkoutDao(database: AppDatabase): YourWorkoutDao {
        return database.yourWorkoutDao()
    }

    @Provides
    fun provideMenuItemDao(database: AppDatabase): MenuItemDao {
        return database.menuItemDao()
    }
}
