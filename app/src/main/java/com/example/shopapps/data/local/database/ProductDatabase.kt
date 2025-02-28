package com.example.shopapps.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shopapps.data.local.dao.CartDao
import com.example.shopapps.data.local.dao.CheckoutDao
import com.example.shopapps.data.local.dao.FavoriteDao
import com.example.shopapps.data.local.dao.NotificationDao
import com.example.shopapps.data.local.dao.OrderDao
import com.example.shopapps.data.local.dao.UserLocationDao
import com.example.shopapps.data.local.entity.CartEntity
import com.example.shopapps.data.local.entity.CheckOutEntity
import com.example.shopapps.data.local.entity.FavoriteEntity
import com.example.shopapps.data.local.entity.NotificationEntity
import com.example.shopapps.data.local.entity.OrderEntity
import com.example.shopapps.data.local.entity.UserLocationEntity

@Database(
    entities = [CartEntity::class,
        CheckOutEntity::class,
        FavoriteEntity::class,
        NotificationEntity::class,
        OrderEntity::class,
        UserLocationEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun checkOutDao(): CheckoutDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun notificationDao(): NotificationDao
    abstract fun orderDao(): OrderDao
    abstract fun userLocationDao(): UserLocationDao
}