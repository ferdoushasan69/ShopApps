package com.example.shopapps.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shopapps.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favoriteentity ORDER BY id DESC")
     fun getAllFavorites(): LiveData<List<FavoriteEntity>>

    @Query("DELETE FROM favoriteentity WHERE id=:id")
    suspend fun deleteFavoriteById(id: Int)

    @Query("SELECT EXISTS (SELECT 1 FROM favoriteentity WHERE product_id=:productId)")
    fun isProductFavorited(productId : Int) : LiveData<Boolean>

    @Query("DELETE FROM favoriteentity")
    suspend fun deleteAllFavorite()
}
