package com.selincengiz.dinefeast.data.model


import com.google.gson.annotations.SerializedName


data class Food(
    @SerializedName("category")
    val category: String?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imageOne")
    val imageOne: String?,
    @SerializedName("imageThree")
    val imageThree: String?,
    @SerializedName("imageTwo")
    val imageTwo: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("rate")
    val rate: Double?,
    @SerializedName("saleState")
    val saleState: Boolean?,
    @SerializedName("title")
    val title: String?,

    @SerializedName("salePrice")
    val salePrice: Double?
)