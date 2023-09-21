package com.selincengiz.dinefeast.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.selincengiz.dinefeast.data.model.FoodEntity

@Dao
interface FoodDao {

    @Query("SELECT * FROM cart_foods ")
    suspend fun getFoods(): List<FoodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFood(food: FoodEntity)

    @Delete
    suspend fun deleteFood(food: FoodEntity)

    @Query("DELETE  FROM cart_foods")
    suspend fun nukeTable()

}