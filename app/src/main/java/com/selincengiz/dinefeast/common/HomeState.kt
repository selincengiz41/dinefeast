package com.selincengiz.dinefeast.common

import com.selincengiz.dinefeast.data.model.Food
import com.selincengiz.dinefeast.data.model.FoodEntity

sealed interface HomeState {
    object Loading : HomeState
    data class Data(val foods: List<FoodEntity>) : HomeState
    data class Category(val categories: List<String>) : HomeState
    data class Error(val throwable: Throwable) : HomeState
    data class DataByFilter(val foods: List<FoodEntity>) : HomeState
    data class Cart(val message: String) : HomeState
    data class Detail(val food: Food) : HomeState

}