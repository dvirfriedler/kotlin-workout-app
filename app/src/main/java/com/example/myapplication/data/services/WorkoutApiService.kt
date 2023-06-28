package com.example.myapplication.data.services

import com.example.myapplication.data.entities.Workout
import retrofit2.Call
import retrofit2.http.GET

interface WorkoutApiService {
    @GET("/exercises")
    fun getExerciseList(): Call<List<Workout>>
}
