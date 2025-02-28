package com.example.shopapps.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shopapps.domain.model.Notification
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "notificationType")
    val notificationType: String,

    @ColumnInfo(name = "firstProductName")
    val firstProductName: String = "",

    @ColumnInfo(name = "quantityCheckout")
    val quantityCheckout: Int = 0,

    @ColumnInfo(name = "firstProductImage")
    val firstProductImage: String = "",

    @ColumnInfo(name = "message")
    val message: String,

    @ColumnInfo(name = "messageDetail")
    val messageDetail: String = "",

    @ColumnInfo(name = "date")
    val date: String = getCurrentFormattedDate(),

    @ColumnInfo(name = "isRead")
    val isRead: Boolean = false
) : Parcelable


    fun NotificationEntity.toDomainNotification()= Notification(
        id = id,
        notificationType = notificationType,
        firstProductName = firstProductName,
        quantityCheckout = quantityCheckout,
        firstProductImage = firstProductImage,
        message = message,
        messageDetail = messageDetail,
        date = date.toLong(),
        isRead = isRead
    )

    fun Notification.toEntityNotification()= NotificationEntity(
        id = id,
        notificationType = notificationType,
        firstProductName = firstProductName,
        quantityCheckout = quantityCheckout,
        firstProductImage = firstProductImage,
        message = message,
        messageDetail = messageDetail,
        date = date.toString(),
        isRead = isRead
    )

