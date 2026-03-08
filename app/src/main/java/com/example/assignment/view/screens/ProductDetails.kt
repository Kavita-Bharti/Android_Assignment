package com.example.assignment.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.assignment.models.Products
import com.example.assignment.viewmodel.ProductViewModel
@Composable
fun ProductDetail(
    navController: NavController,
    viewModel: ProductViewModel = hiltViewModel()
) {

    val product =
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<Products>("product")

    if (product == null) {
        Text("Product not found")
        return
    }

    product?.let { item ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {

                LazyRow {

                    items(item.images) { image ->

                        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

                        AsyncImage(
                            model = image,
                            contentDescription = item.title,
                            modifier = Modifier
                                .height(screenHeight * 0.6f)
                                .fillMaxWidth()
                                .padding(end = 10.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            item {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = "₹${item.price}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "-${item.discountPercentage}%",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {

                Row(
                    modifier = Modifier
                        .background(
                            Color(0xFF2E7D32),
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {

                    Text(
                        text = item.rating.toString(),
                        color = Color.White
                    )

                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Text(
                    text = "Brand: ${item.brand}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Text(
                    text = "Category: ${item.category}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            item {

                val stockColor =
                    if (item.stock > 0) Color(0xFF2E7D32)
                    else Color.Red

                Text(
                    text = "Only ${item.stock} left",
                    color = stockColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            item {

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}