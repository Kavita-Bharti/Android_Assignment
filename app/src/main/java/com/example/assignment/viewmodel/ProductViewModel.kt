package com.example.assignment.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.data.local.FavouriteProduct
import com.example.assignment.data.repository.ProductRepository
import com.example.assignment.models.Category
import com.example.assignment.models.Products
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    var skip = 0
    private val limit = 10
    var isLoading by mutableStateOf(false)
        private set
    var products by mutableStateOf<List<Products>>(emptyList())
        private set

    var categories by mutableStateOf<List<Category>>(emptyList())
        private set

    var selectedCategory by mutableStateOf("All")
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    val favorites = repository.getFavorites()
    init {
        loadProducts()
        loadCategories()
    }

    fun loadProducts() {
        if (isLoading) return
        viewModelScope.launch {
            try {
                isLoading = true
                val response =
                    repository.getProducts(limit, skip)
                products = products + response.products
                skip += limit
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun getProductById(id: Int): Products? {
        return products.find { it.id == id }
    }
    fun loadCategories() {
        viewModelScope.launch {
            try {
                categories = repository.getCategories()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun filterByCategorySlug(slug: String) {

        viewModelScope.launch {

            if (slug == "all") {
                loadProducts()
            } else {

                val response = repository.getProductsByCategory(slug)
                products = response.products
            }
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            val response = repository.searchProducts(query)
            products = response.products
        }
    }

    fun addFavorite(product: Products) {
        viewModelScope.launch {
            repository.addFavorite(
                FavouriteProduct(
                    product.id,
                    product.title,
                    product.price,
                    product.thumbnail
                )
            )
        }
    }

    fun removeFavorite(productId: Int) {
        viewModelScope.launch {
            repository.removeFavorite(productId)
        }
    }
}