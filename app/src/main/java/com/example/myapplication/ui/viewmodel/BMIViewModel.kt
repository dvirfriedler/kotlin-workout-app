package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.entities.BMIResponse
import com.example.myapplication.data.repository.BMIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BMIViewModel @Inject constructor(private val repository: BMIRepository) : ViewModel() {

    private val _bmi = MutableLiveData<BMIResponse>()
    val bmi: LiveData<BMIResponse> = _bmi

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getBMI(age: Int, height: Int, system: String) {
        viewModelScope.launch {
            try {
                val result = repository.getBMI(age, height, system)
                result?.let {
                    _bmi.value = it
                }
            } catch (e: Exception) {

                println("BMIViewModel")
                println(e)
                //throw Exception("Failed to fetch BMI", e)

                Log.e("BMIViewModel", "Failed to get BMI", e)

                // If you want to display an error message to the user, you can post a value to a LiveData here
                _errorMessage.postValue("Failed to get BMI due to network issues.")
            }
        }
    }
}
