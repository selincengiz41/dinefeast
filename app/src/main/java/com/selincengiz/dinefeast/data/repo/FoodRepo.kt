package com.selincengiz.dinefeast.data.repo

import com.google.firebase.auth.FirebaseAuth
import com.selincengiz.dinefeast.common.Resource
import com.selincengiz.dinefeast.data.model.Food
import com.selincengiz.dinefeast.data.source.local.FoodDao
import com.selincengiz.dinefeast.data.source.remote.FoodService
import java.lang.Exception

class FoodRepo(
    private val foodService: FoodService,
    private val foodDao: FoodDao,
    private val auth: FirebaseAuth
) {

    suspend fun getFoods(): Resource<List<Food>> {

        return try {
            Resource.Success(foodService.getFoods().foods.orEmpty())
        } catch (e: Exception) {

            Resource.Error(e)
        }
    }

    suspend fun getFoodDetail(id: Int): Resource<Food> {
        return try {
            foodService.getFoodDetail(id).food?.let {
                Resource.Success(it)
            } ?: kotlin.run {
                Resource.Error(Exception("Food not found!"))
            }
        } catch (e: Exception) {

            Resource.Error(e)
        }

    }

    suspend fun getCategories(): Resource<List<String>> {

        return try {
            Resource.Success(foodService.getCategories().categories.orEmpty())
        } catch (e: Exception) {

            Resource.Error(e)
        }
    }

    suspend fun getFoodsByCategory(category: String): Resource<List<Food>> {

        return try {
            Resource.Success(foodService.getFoodsByCategory(category).foods.orEmpty())
        } catch (e: Exception) {

            Resource.Error(e)
        }

    }

    suspend fun getSaleFoods():Resource<List<Food>>  {

        return try {
            Resource.Success(foodService.getSaleFoods().foods.orEmpty())
        } catch (e: Exception) {

            Resource.Error(e)
        }
    }

    suspend fun searchFoods(query:String):Resource<List<Food>>  {

        return try {
            Resource.Success(foodService.searchFood(query).foods.orEmpty())
        } catch (e: Exception) {

            Resource.Error(e)
        }
    }

}