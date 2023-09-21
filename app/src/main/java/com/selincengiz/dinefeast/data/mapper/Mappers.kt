package com.selincengiz.dinefeast.data.mapper

import com.selincengiz.dinefeast.data.model.Food
import com.selincengiz.dinefeast.data.model.FoodEntity

fun Food.mapToFoodEntity(isFavorite: Boolean): FoodEntity {
    return FoodEntity(
        id,
        category,
        count,
        description,
        imageOne,
        imageThree,
        imageTwo,
        price,
        rate,
        saleState,
        title,
        salePrice,
        isFavorite
    )
}

fun FoodEntity.mapToFavorites(isFavorite: Boolean): FoodEntity {
    return return FoodEntity(
        id,
        category,
        count,
        description,
        imageOne,
        imageThree,
        imageTwo,
        price,
        rate,
        saleState,
        title,
        salePrice,
        isFavorite
    )
}


fun FoodEntity.mapToFood(): Food {
    return Food(
        category,
        count,
        description,
        id,
        imageOne,
        imageThree,
        imageTwo,
        price,
        rate,
        saleState,
        title,
        salePrice
    )
}