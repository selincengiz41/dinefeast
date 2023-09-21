package com.selincengiz.dinefeast.data.repo

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.firebase.auth.FirebaseAuth
import com.selincengiz.dinefeast.common.Resource
import com.selincengiz.dinefeast.data.mapper.mapToFood
import com.selincengiz.dinefeast.data.mapper.mapToFoodEntity
import com.selincengiz.dinefeast.data.model.Food
import com.selincengiz.dinefeast.data.model.FoodEntity
import com.selincengiz.dinefeast.data.source.local.FoodDao
import com.selincengiz.dinefeast.data.source.remote.FoodService
import java.lang.Exception

class FavoriteRepo(
    private val foodService: FoodService,
    private val foodDao: FoodDao,
    private val auth: FirebaseAuth
) {

    suspend fun getFoods(): List<FoodEntity> {

        return foodDao.getFoods()
    }

    suspend fun addFood(food: FoodEntity) {

        foodDao.addFood(food)
    }


    suspend fun deleteFood(food: FoodEntity) {
        foodDao.deleteFood(food)
    }

}