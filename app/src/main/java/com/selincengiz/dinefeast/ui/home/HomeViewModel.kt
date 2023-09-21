package com.selincengiz.dinefeast.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selincengiz.dinefeast.common.HomeState
import com.selincengiz.dinefeast.common.Resource
import com.selincengiz.dinefeast.data.mapper.mapToFoodEntity
import com.selincengiz.dinefeast.data.model.Food
import com.selincengiz.dinefeast.data.model.FoodEntity
import com.selincengiz.dinefeast.data.repo.CartRepo
import com.selincengiz.dinefeast.data.repo.FavoriteRepo
import com.selincengiz.dinefeast.data.repo.FoodRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val foodRepo: FoodRepo ,val cartRepo: CartRepo, val favoriteRepo: FavoriteRepo): ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState : LiveData<HomeState>
        get() = _homeState

    private var _roomList=MutableLiveData<List<FoodEntity>>()
    val roomList:LiveData<List<FoodEntity>>
        get() = _roomList

    fun getFoods(){
        viewModelScope.launch {
            _homeState.value= HomeState.Loading
            val result=  foodRepo.getFoods()
            when(result){
                is Resource.Success -> {
                    val entityList = favoriteRepo.getFoods()
                    var finalList= ArrayList<FoodEntity>()
                    if (entityList.size > 0) {
                        result.data.forEach { food ->
                            var temp: FoodEntity? =null
                            entityList.forEach { foodEntity ->

                                if (food.id == foodEntity.id) {
                                    temp = food.mapToFoodEntity(true)
                                }

                            }
                            temp?.let {
                                finalList.add(temp!!)
                            } ?: kotlin.run {
                                finalList.add(food.mapToFoodEntity(false))
                            }

                        }
                        _roomList.value= finalList
                    }
                    else {
                        _roomList.value=   result.data.map { it.mapToFoodEntity(false) }
                    }
                    _homeState.value= _roomList.value?.let { HomeState.Data(it) }
                }

                is Resource.Error -> {
                    _homeState.value=HomeState.Error(result.throwable)
                }
            }
        }
    }


    fun getSaleFoods(){
        viewModelScope.launch {
            _homeState.value= HomeState.Loading
            val result=  foodRepo.getSaleFoods()
            when(result){
                is Resource.Success -> {
                    val entityList = favoriteRepo.getFoods()
                    var finalList= ArrayList<FoodEntity>()
                    if (entityList.size > 0) {
                        result.data.forEach { food ->
                            var temp: FoodEntity? =null
                            entityList.forEach { foodEntity ->

                                if (food.id == foodEntity.id) {
                                    temp = food.mapToFoodEntity(true)
                                }

                            }
                            temp?.let {
                                finalList.add(temp!!)
                            } ?: kotlin.run {
                                finalList.add(food.mapToFoodEntity(false))
                            }

                        }
                        _roomList.value= finalList
                    }
                    else {
                        _roomList.value=   result.data.map { it.mapToFoodEntity(false) }
                    }
                    _homeState.value= _roomList.value?.let { HomeState.DataByFilter(it) }

                }

                is Resource.Error -> {
                    _homeState.value=HomeState.Error(result.throwable)
                }
            }
        }
    }

    fun addCart(foodId:Int){
        viewModelScope.launch {
            _homeState.value= HomeState.Loading
            val result=  cartRepo.addCart(foodId)
            when(result){
                is Resource.Success -> {
                    _homeState.value= HomeState.Cart(result.data)
                }

                is Resource.Error -> {
                    _homeState.value=HomeState.Error(result.throwable)
                }
            }
        }
    }




    fun addFavorites(food: FoodEntity) {
        viewModelScope.launch {
            favoriteRepo.addFood(food)
        }

    }

    fun deleteFavorites(food: FoodEntity) {
        viewModelScope.launch {
            favoriteRepo.deleteFood(food)
        }
    }

}