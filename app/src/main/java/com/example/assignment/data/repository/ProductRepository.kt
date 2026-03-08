package com.example.assignment.data.repository

import com.example.assignment.data.api.ProductServiceApi
import com.example.assignment.data.local.FavouriteDao
import com.example.assignment.data.local.FavouriteProduct
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ProductRepository @Inject constructor(
    private val api: ProductServiceApi,
    private val dao: FavouriteDao
) {

    suspend fun getProducts(limit: Int, skip: Int) =
        api.getProducts(limit, skip)

    suspend fun searchProducts(query: String) =
        api.searchProducts(query)

    suspend fun getCategories() =
        api.getCategories()

    suspend fun getProductsByCategory(category: String) =
        api.getProductsByCategory(category)

    suspend fun addFavorite(product: FavouriteProduct) =
        dao.insert(product)

    fun getFavorites() =
        dao.getFavorites()

    suspend fun removeFavorite(productId: Int) {
        dao.delete(productId)
    }
}