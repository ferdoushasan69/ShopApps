package com.example.shopapps.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shopapps.domain.model.Order
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "total_price")
    val totalPrice: Double,

    @ColumnInfo(name = "order_date")
    val orderDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "items")
    val items: List<CartEntity>
) : Parcelable
    fun OrderEntity.toDomainOrder() = Order(
        id = id,
        totalPrice =totalPrice,
        orderDate = orderDate,
        items = items.map { it.toCartDomain() }
    )

    fun Order.toEntityOrder()= OrderEntity(
        id = id,
        totalPrice =totalPrice,
        orderDate = orderDate,
        items = items.map { it.toEntityCart() }
    )

