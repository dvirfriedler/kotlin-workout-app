package com.example.myapplication.data.repository

import com.example.myapplication.data.entities.BMIResponse
import com.example.myapplication.data.services.BMIApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BMIRepository @Inject constructor(private val bmiApiService: BMIApiService) {

    suspend fun getBMI(age: Int, height: Int, system: String): BMIResponse? {
        try {
            val response = withContext(Dispatchers.IO) {
                bmiApiService.getBMI(age, height, system).execute()
            }

            println("response")
            println(response)
            if (response.isSuccessful) {
                return response.body()
            } else {
                println("else")
                // Handle unsuccessful response
                throw Exception("Failed to get BMI")
            }
        } catch (e: Exception) {
            println("BMIRepository")
            println(e)
            throw Exception("Failed to get BMI", e)
        }
    }
}

