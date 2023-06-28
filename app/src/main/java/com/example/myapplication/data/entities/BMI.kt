package com.example.myapplication.data.entities

import com.google.gson.annotations.SerializedName

data class BMIResponse(
    @SerializedName("BMI")
    val bmi: Double,
    @SerializedName("Class")
    val bmiClass: String
)
