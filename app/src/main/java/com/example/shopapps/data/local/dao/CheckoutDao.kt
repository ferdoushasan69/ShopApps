package com.example.shopapps.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shopapps.data.local.entity.CheckOutEntity

@Dao
interface CheckoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheckOut(checkOutEntity: CheckOutEntity)

    @Query("SELECT * FROM checkoutentity")
    fun getAllCheckOut(): LiveData<List<CheckOutEntity>>

    @Query("SELECT * FROM checkoutentity ORDER BY checkout_date DESC LIMIT 1")
    fun getLatestCheckOut(): LiveData<CheckOutEntity>

    @Query("UPDATE checkoutentity SET payment_method=:paymentMethod WHERE id=:id")
    suspend fun updatePaymentMethod(paymentMethod: String, id: Int)

    @Query("DELETE FROM checkoutentity")
    suspend fun deleteAllCheckOut()
}