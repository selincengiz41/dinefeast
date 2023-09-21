package com.selincengiz.dinefeast.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.selincengiz.dinefeast.data.model.FoodEntity

@Database(entities = [FoodEntity::class], version = 1)
abstract class FoodRoomDB : RoomDatabase() {

    abstract fun foodDao(): FoodDao
}