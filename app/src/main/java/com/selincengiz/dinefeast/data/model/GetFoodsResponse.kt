package com.selincengiz.dinefeast.data.model


import com.google.gson.annotations.SerializedName

data class GetFoodsResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("products")
    val foods: List<Food>?,
    @SerializedName("status")
    val status: Int?
)