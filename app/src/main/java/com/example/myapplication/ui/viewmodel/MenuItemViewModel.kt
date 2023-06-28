package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.entities.MenuItem
import com.example.myapplication.data.repository.MenuItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuItemViewModel @Inject constructor(private val repository: MenuItemRepository) : ViewModel() {
    val menuItems: LiveData<List<MenuItem>> = repository.menuItems

    private val _loadingStatus = MutableLiveData<Boolean>()
    val loadingStatus: LiveData<Boolean> = _loadingStatus

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchFilteredMenuItems(minValue: Float, maxValue: Float) {
        viewModelScope.launch {
            try {
                _loadingStatus.value = true
                _errorMessage.value = ""

                val fetchedItems = repository.fetchFilteredMenuItems(minValue, maxValue)

                if (fetchedItems.isEmpty()) {
                    _errorMessage.value = "No menu items found"
                }

                _loadingStatus.value = false
            } catch (e: Exception) {
                _loadingStatus.value = false
                // Log the exception
                Log.e("MenuItemViewModel", "Failed to fetch Menu from the Internet", e)
                // If you want to display an error message to the user, you can post a value to a LiveData here
                _errorMessage.postValue("Failed to fetch Menu due to network issues.")
            }
        }
    }
}
