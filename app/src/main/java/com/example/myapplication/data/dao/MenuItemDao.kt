package com.example.myapplication.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.entities.MenuItem

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM menuItems")
    fun getMenuItems(): LiveData<List<MenuItem>>

    @Query("SELECT * FROM menuItems WHERE id = :id")
    fun getMenuItemById(id: Int): LiveData<MenuItem?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateMenuItem(menuItem: MenuItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateMenuItems(menuItems: List<MenuItem>)

    @Delete
    fun deleteMenuItem(menuItem: MenuItem)

    @Query("DELETE FROM menuItems")
    fun deleteAll()
}
