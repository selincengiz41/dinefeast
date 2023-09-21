package com.selincengiz.dinefeast.data.source.remote

import com.selincengiz.dinefeast.data.model.AddToCartRequest
import com.selincengiz.dinefeast.data.model.BaseResponse
import com.selincengiz.dinefeast.data.model.ClearToCartRequest
import com.selincengiz.dinefeast.data.model.DeleteToCartRequest
import com.selincengiz.dinefeast.data.model.GetCategoriesResponse
import com.selincengiz.dinefeast.data.model.GetFoodDetailResponse
import com.selincengiz.dinefeast.data.model.GetFoodsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FoodService {

    @POST("add_to_cart.php")
    suspend fun addCart(@Body addToCartRequest: AddToCartRequest): BaseResponse

    @POST("delete_from_cart.php")
    suspend fun deleteCart(@Body deleteToCartRequest: DeleteToCartRequest): BaseResponse

    @GET("get_cart_products.php")
    suspend fun getCartFoods(@Query("userId") userId: String): GetFoodsResponse

    @POST("clear_cart.php")
    suspend fun clearCart(@Body clearToCartRequest: ClearToCartRequest): BaseResponse

    @GET("get_products.php")
    suspend fun getFoods(): GetFoodsResponse

    @GET("get_products_by_category.php")
    suspend fun getFoodsByCategory(@Query("category") category: String): GetFoodsResponse

    @GET("get_sale_products.php")
    suspend fun getSaleFoods(): GetFoodsResponse

    @GET("search_product.php")
    suspend fun searchFood(@Query("query") query: String): GetFoodsResponse

    @GET("get_categories.php")
    suspend fun getCategories(): GetCategoriesResponse

    @GET("get_product_detail.php")
    suspend fun getFoodDetail(@Query("id") id: Int): GetFoodDetailResponse
}