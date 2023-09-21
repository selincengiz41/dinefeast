package com.selincengiz.dinefeast.data.model


import com.google.gson.annotations.SerializedName

data class GetCategoriesResponse(
    @SerializedName("categories")
    val categories: List<String>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)