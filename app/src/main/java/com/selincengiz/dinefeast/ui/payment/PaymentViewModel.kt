package com.selincengiz.dinefeast.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selincengiz.dinefeast.common.HomeState
import com.selincengiz.dinefeast.common.Resource
import com.selincengiz.dinefeast.data.repo.CartRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val cartRepo: CartRepo) : ViewModel() {


    fun clearCart() {
        viewModelScope.launch {
            cartRepo.clearCart()
        }
    }
}