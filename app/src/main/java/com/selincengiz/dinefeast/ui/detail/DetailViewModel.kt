package com.selincengiz.dinefeast.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selincengiz.dinefeast.common.HomeState
import com.selincengiz.dinefeast.common.Resource
import com.selincengiz.dinefeast.data.repo.CartRepo
import com.selincengiz.dinefeast.data.repo.FoodRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val foodRepo: FoodRepo,
    private val cartRepo: CartRepo
) : ViewModel() {


    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState>
        get() = _homeState


    fun getFoodDetail(foodId: Int) {
        viewModelScope.launch {
            _homeState.value = HomeState.Loading
            val result = foodRepo.getFoodDetail(foodId)
            when (result) {
                is Resource.Success -> {
                    _homeState.value = HomeState.Detail(result.data)
                }

                is Resource.Error -> {
                    _homeState.value = HomeState.Error(result.throwable)
                }
            }
        }
    }

    fun addCart(foodId: Int) {
        viewModelScope.launch {
            _homeState.value = HomeState.Loading
            val result = cartRepo.addCart(foodId)
            when (result) {
                is Resource.Success -> {
                    _homeState.value = HomeState.Cart(result.data)
                }

                is Resource.Error -> {
                    _homeState.value = HomeState.Error(result.throwable)
                }
            }
        }
    }
}