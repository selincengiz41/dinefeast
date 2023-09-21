package com.selincengiz.dinefeast.data.model


import com.google.gson.annotations.SerializedName

data class GetFoodDetailResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("product")
    val food: Food?,
    @SerializedName("status")
    val status: Int?
)