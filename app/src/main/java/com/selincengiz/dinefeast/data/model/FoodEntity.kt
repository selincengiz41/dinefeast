package com.selincengiz.dinefeast.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cart_foods")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int?,

    @ColumnInfo("category")
    val category: String?,

    @ColumnInfo("count")
    val count: Int?,

    @ColumnInfo("description")
    val description: String?,

    @ColumnInfo("imageOne")
    val imageOne: String?,

    @ColumnInfo("imageThree")
    val imageThree: String?,

    @ColumnInfo("imageTwo")
    val imageTwo: String?,

    @ColumnInfo("price")
    val price: Double?,

    @ColumnInfo("rate")
    val rate: Double?,

    @ColumnInfo("saleState")
    val saleState: Boolean?,

    @ColumnInfo("title")
    val title: String?,

    @ColumnInfo("salePrice")
    val salePrice: Double?,

    @ColumnInfo("isFavorite")
    val isFavorite: Boolean?,


)
