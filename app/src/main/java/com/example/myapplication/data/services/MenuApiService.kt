package com.example.myapplication.data.services


import com.example.myapplication.data.entities.MenuItem

import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

interface MenuApiService {
    @GET("/")
    fun getFilteredMenuItems(
        @Query("calories__gt") minValue: Float,
        @Query("calories__lt") maxValue: Float
    ): Call<List<MenuItem>>
}
