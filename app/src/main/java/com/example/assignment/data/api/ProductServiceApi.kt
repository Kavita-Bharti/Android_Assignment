package com.example.assignment.data.api

import com.example.assignment.models.Category
import com.example.assignment.models.productResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductServiceApi {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): productResponse

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") query: String
    ): productResponse

    @GET("products/categories")
    suspend fun getCategories(): List<Category>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String
    ): productResponse
}