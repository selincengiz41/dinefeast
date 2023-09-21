package com.selincengiz.dinefeast.data.repo

import com.google.firebase.auth.FirebaseAuth
import com.selincengiz.dinefeast.common.Resource
import com.selincengiz.dinefeast.data.model.AddToCartRequest
import com.selincengiz.dinefeast.data.model.ClearToCartRequest
import com.selincengiz.dinefeast.data.model.DeleteToCartRequest
import com.selincengiz.dinefeast.data.model.Food
import com.selincengiz.dinefeast.data.source.local.FoodDao
import com.selincengiz.dinefeast.data.source.remote.FoodService
import java.lang.Exception

class CartRepo(
    private val foodService: FoodService,
    private val foodDao: FoodDao,
    private val auth: FirebaseAuth
) {

    suspend fun addCart(foodId: Int): Resource<String> {

        val addToCartRequest = AddToCartRequest(auth.currentUser!!.uid, foodId)
        return try {
            foodService.addCart(addToCartRequest).message?.let {
                Resource.Success(it)
            } ?: kotlin.run {
                Resource.Error(Exception("Adding failed"))
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun deleteCart(productId: Int): Resource<String> {
        val deleteToCartRequest = DeleteToCartRequest(productId)
        return try {
            foodService.deleteCart(deleteToCartRequest).message?.let {
                Resource.Success(it)
            } ?: kotlin.run {
                Resource.Error(Exception("Deletion failed"))
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getCartFoods(): Resource<List<Food>> {

        return try {
            Resource.Success(foodService.getCartFoods(auth.currentUser!!.uid).foods.orEmpty())
        } catch (e: Exception) {

            Resource.Error(e)
        }
    }

    suspend fun clearCart(): Resource<String> {
        val clearToCartRequest = ClearToCartRequest(auth.currentUser!!.uid)

        return try {
            foodService.clearCart(clearToCartRequest).message?.let {
                Resource.Success(it)
            } ?: kotlin.run {
                Resource.Error(Exception("Clean failed"))
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


}