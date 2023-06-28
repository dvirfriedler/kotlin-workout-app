package com.example.myapplication.data.services

import com.example.myapplication.data.entities.BMIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BMIApiService {
    @GET("/bmi")
    fun getBMI(
        @Query("height") height: Int,
        @Query("weight") weight: Int,
        @Query("system") system: String,
        ): Call<BMIResponse>
}

