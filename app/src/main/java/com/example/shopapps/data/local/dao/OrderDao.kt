package com.example.shopapps.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shopapps.data.local.entity.OrderEntity

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orderEntity: OrderEntity)

    @Query("SELECT * FROM orderentity ORDER BY order_date DESC")
    fun getAllOrder(): LiveData<List<OrderEntity>>

    @Query("SELECT * FROM orderentity ORDER BY order_date DESC LIMIT 1")
    fun getLatestOrder(): LiveData<OrderEntity>

    @Query("DELETE FROM orderentity WHERE id=:orderId")
    suspend fun deleteOrderById(orderId: Int)

    @Query("DELETE FROM orderentity")
    suspend fun deleteAllOrder()
}