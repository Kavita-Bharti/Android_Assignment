package com.example.assignment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assignment.view.screens.FavoriteScreen
import com.example.assignment.view.screens.ProductDetail
import com.example.assignment.view.screens.ProductList

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "products"
    ) {

        composable("products") {
            ProductList(navController = navController)
        }


        composable("favorites") {
            FavoriteScreen()
        }


        composable("detail") {
            ProductDetail(navController = navController)
        }

    }
}