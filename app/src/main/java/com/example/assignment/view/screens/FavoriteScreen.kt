package com.example.assignment.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.assignment.view.components.EmptyState
import com.example.assignment.view.components.FavoriteCard
import com.example.assignment.view.components.ProductCard
import com.example.assignment.view.components.SearchBar
import com.example.assignment.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: ProductViewModel = hiltViewModel()
) {

    val favoriteProducts by viewModel.favorites.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites") }
            )
        }
    ) { padding ->

        if (favoriteProducts.isEmpty()) {
            EmptyState(
                message = "No favorite products yet"
            )

        } else {

            LazyColumn(
                modifier = Modifier.padding(padding)
            ) {

                items(favoriteProducts) { product ->

                    FavoriteCard(
                        product = product,
                        onClick = { },
                        onRemove = {
                            viewModel.removeFavorite(product.id)
                        }
                    )
                }
            }
        }
    }
}