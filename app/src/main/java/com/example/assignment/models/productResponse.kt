package com.example.assignment.models

data class productResponse(

    val products: List<Products>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
