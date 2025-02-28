package com.example.shopapps.data.di

import android.content.Context
import androidx.room.Room
import com.example.shopapps.data.local.dao.CartDao
import com.example.shopapps.data.local.dao.CheckoutDao
import com.example.shopapps.data.local.dao.FavoriteDao
import com.example.shopapps.data.local.dao.NotificationDao
import com.example.shopapps.data.local.dao.OrderDao
import com.example.shopapps.data.local.dao.UserLocationDao
import com.example.shopapps.data.local.database.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): ProductDatabase {
        return Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            "product_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCartDao(db: ProductDatabase): CartDao {
        return db.cartDao()
    }

    @Provides
    fun providesCheckOutDao(db: ProductDatabase): CheckoutDao {
        return db.checkOutDao()
    }

    @Provides
    fun provideFavoriteDao(db: ProductDatabase): FavoriteDao {
        return db.favoriteDao()
    }

    @Provides
    fun provideNotificationDao(db: ProductDatabase): NotificationDao {
        return db.notificationDao()
    }

    @Provides
    fun provideOrderDao(db: ProductDatabase): OrderDao {
        return db.orderDao()
    }

    @Provides
    fun provideUserLocationDao(db: ProductDatabase): UserLocationDao {
        return db.userLocationDao()
    }
}