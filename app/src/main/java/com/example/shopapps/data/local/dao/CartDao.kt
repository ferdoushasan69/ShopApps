package com.example.shopapps.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shopapps.data.local.entity.CartEntity
@Dao
interface CartDao {
    @Query("SELECT * FROM cartentity WHERE product_id=:productId LIMIT 1")
    suspend fun getCartItemByProductId(productId: Int): CartEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cartEntity: CartEntity)

    @Query("SELECT * FROM cartentity ORDER BY id DESC")
     fun getAllCart(): LiveData<List<CartEntity>>

    @Query("UPDATE cartentity SET product_quantity=:quantity WHERE id=:cartId")
    suspend fun updateQuantity(quantity : Int,cartId : Int)

    @Query("DELETE FROM cartentity WHERE id=:id")
    suspend fun deleteCartById(id : Int)

    @Query("DELETE FROM cartentity")
    suspend fun deleteAllCartItem()

}