package com.selincengiz.dinefeast.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.selincengiz.dinefeast.data.repo.CartRepo
import com.selincengiz.dinefeast.data.repo.FavoriteRepo
import com.selincengiz.dinefeast.data.repo.FoodRepo
import com.selincengiz.dinefeast.data.source.local.FoodDao
import com.selincengiz.dinefeast.data.source.remote.FoodService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideRepo(foodService: FoodService, foodDao: FoodDao, auth: FirebaseAuth) =
        FoodRepo(foodService = foodService, foodDao = foodDao, auth = auth)


    @Provides
    @Singleton
    fun provideCartRepo(foodService: FoodService, foodDao: FoodDao, auth: FirebaseAuth) =
        CartRepo(foodService = foodService, foodDao = foodDao, auth = auth)


    @Provides
    @Singleton
    fun provideFavoriteRepo(foodService: FoodService, foodDao: FoodDao, auth: FirebaseAuth) =
        FavoriteRepo(foodService = foodService, foodDao = foodDao, auth = auth)

}