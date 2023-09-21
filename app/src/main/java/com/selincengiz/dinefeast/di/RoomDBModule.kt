package com.selincengiz.dinefeast.di

import android.content.Context
import androidx.room.Room
import com.selincengiz.dinefeast.data.source.local.FoodRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {

    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, FoodRoomDB::class.java, "foods_room_db").build()

    @Provides
    @Singleton
    fun provideRoomDao(roomDB: FoodRoomDB)=roomDB.foodDao()
}