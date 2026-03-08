package com.example.assignment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteProduct(

    @PrimaryKey
    val id: Int,

    val title: String,
    val price: Double,
    val image: String
)
