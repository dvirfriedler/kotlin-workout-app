package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.dao.MenuItemDao
import com.example.myapplication.data.entities.MenuItem
import com.example.myapplication.data.services.MenuApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuItemRepository @Inject constructor(
    private val menuItemDao: MenuItemDao, private val menuApiService: MenuApiService
) {
    val menuItems: LiveData<List<MenuItem>> = menuItemDao.getMenuItems()

    suspend fun fetchFilteredMenuItems(minValue: Float, maxValue: Float): List<MenuItem> {
        try {
            val response = withContext(Dispatchers.IO) {
                menuApiService.getFilteredMenuItems(minValue, maxValue).execute()
            }

            deleteAll()

            if (response.isSuccessful) {
                val menuItems = response.body()

                menuItems?.let {
                    insertOrUpdateMenuItems(it)
                    return it
                }
            } else {
                // Handle unsuccessful response
                throw Exception("Failed to fetch filtered menu items")
            }
        } catch (e: Exception) {
            // Handle failure
            throw Exception("Failed to fetch filtered menu items", e)
        }
        return emptyList()
    }

    private suspend fun insertOrUpdateMenuItems(menuItems: List<MenuItem>) {
        withContext(Dispatchers.IO) {
            menuItemDao.insertOrUpdateMenuItems(menuItems)
        }
    }

    private suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            menuItemDao.deleteAll()
        }
    }
}
