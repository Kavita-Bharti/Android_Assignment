package com.example.assignment.view.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.assignment.view.components.EmptyState
import com.example.assignment.view.components.FilterBottomSheet
import com.example.assignment.view.components.ProductCard
import com.example.assignment.view.components.SearchBar
import com.example.assignment.viewmodel.ProductViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProductList(
    viewModel: ProductViewModel = hiltViewModel(),
    navController: NavController
) {

    val products = viewModel.products
    val categories = viewModel.categories
    val favorites by viewModel.favorites.collectAsState(initial = emptyList())
    var showFilter by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
        viewModel.loadCategories()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Products")
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate("favorites") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->


        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                SearchBar(
                    onSearch = {
                        viewModel.searchProducts(it)
                    },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                       showFilter = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filter"
                    )
                }
            }

            val refreshState = rememberPullRefreshState(
                refreshing = viewModel.isLoading,
                onRefresh = {
                    viewModel.loadProducts()
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .pullRefresh(refreshState)
            ) {

                 if (products.isEmpty()) {
                    EmptyState(
                        message = "No products found"
                    )

                } else {
                     LazyColumn {

                         items(products.size) { index ->

                             val product = products[index]
                             val isFavorite = favorites.any { it.id == product.id }

                             ProductCard(
                                 product = product,
                                 onClick = {
                                     navController.currentBackStackEntry
                                         ?.savedStateHandle
                                         ?.set("product", product)

                                     navController.navigate("detail")
                                 },
                                 isFavorite = isFavorite,
                                 onFavorite = {
                                     if (isFavorite) {
                                         viewModel.removeFavorite(product.id)
                                     } else {
                                         viewModel.addFavorite(product)
                                     }
                                 }
                             )

                             if (index == products.lastIndex) {
                                 LaunchedEffect(Unit) {
                                     viewModel.loadProducts()
                                 }
                             }
                         }
                     }
                }

                PullRefreshIndicator(
                    refreshing = viewModel.isLoading,
                    state = refreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }

    if (showFilter) {
        FilterBottomSheet(
            categories = viewModel.categories,
            onSelectCategory = { slug ->
                viewModel.filterByCategorySlug(slug)
            },
            onDismiss = {
                showFilter = false
            }
        )
    }
}

