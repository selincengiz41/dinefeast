package com.selincengiz.dinefeast.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selincengiz.dinefeast.common.HomeState
import com.selincengiz.dinefeast.common.Resource
import com.selincengiz.dinefeast.data.mapper.mapToFood
import com.selincengiz.dinefeast.data.mapper.mapToFoodEntity
import com.selincengiz.dinefeast.data.model.Food
import com.selincengiz.dinefeast.data.model.FoodEntity
import com.selincengiz.dinefeast.data.repo.CartRepo
import com.selincengiz.dinefeast.data.repo.FavoriteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val favoriteRepo: FavoriteRepo ,private val cartRepo: CartRepo): ViewModel() {

    private var _listFavorite = MutableLiveData<List<FoodEntity>>()
    val listFavorite : LiveData<List<FoodEntity>>
        get() = _listFavorite

    fun getFoods(){
        viewModelScope.launch {

            _listFavorite.value = favoriteRepo.getFoods()

        }

    }

    fun addCart(foodId: Int) {
        viewModelScope.launch {
             cartRepo.addCart(foodId)

        }
    }

    fun addFood(food: FoodEntity) {
        viewModelScope.launch {
            favoriteRepo.addFood(food)
        }

    }


    fun deleteFood(food: FoodEntity) {
        viewModelScope.launch {
            favoriteRepo.deleteFood(food)
        }
    }

}