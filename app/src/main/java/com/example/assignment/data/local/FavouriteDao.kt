package com.example.assignment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: FavouriteProduct)

    @Query("SELECT * FROM FavouriteProduct ")
    fun getFavorites(): Flow<List<FavouriteProduct>>

    @Query("DELETE FROM FavouriteProduct WHERE id = :productId")
    suspend fun delete(productId: Int)
}