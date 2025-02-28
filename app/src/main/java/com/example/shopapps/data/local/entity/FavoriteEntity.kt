package com.example.shopapps.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shopapps.domain.model.Favorite
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "product_id")
    var productId: Int,

    @ColumnInfo(name = "product_name")
    var productName: String,

    @ColumnInfo(name = "product_price")
    var productPrice: String,

    @ColumnInfo(name = "product_image")
    var productImage: String,

    @ColumnInfo(name = "product_category")
    var productCategory: String,

    @ColumnInfo(name = "product_quantity")
    var productQuantity: Int
) : Parcelable
    fun FavoriteEntity.toDomainFavorite()= Favorite(
        id = id,
        productId =productId,
        productName = productName,
        productPrice = productPrice,
        productImage = productImage,
        productCategory = productCategory,
        productQuantity = productQuantity
    )

    fun Favorite.toEntityFavorite()= FavoriteEntity(
        id = id,
        productId =productId,
        productName = productName,
        productPrice = productPrice,
        productImage = productImage,
        productCategory = productCategory,
        productQuantity = productQuantity
    )


