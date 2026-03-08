package com.example.assignment.models
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
@Parcelize
data class Products(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String?,
    val thumbnail: String,
    val images: List<String>
): Parcelable
