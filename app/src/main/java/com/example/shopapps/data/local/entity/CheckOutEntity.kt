package com.example.shopapps.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shopapps.domain.model.CheckOut
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Entity
@Parcelize
data class CheckOutEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "receiver_name")
    val receiverName: String? = "",

    @ColumnInfo(name = "receiver_address")
    val receiverAddress: String?="",

    @ColumnInfo(name = "order_items")
    val orderItems: List<CartEntity> = listOf(),

    @ColumnInfo(name = "shipping_method")
    val shippingMethod: String? ="",

    @ColumnInfo(name = "coupon")
    val coupon: String? ="",

    @ColumnInfo(name = "formatted_order_date")
    val formattedCheckoutDate: String? = getCurrentFormattedDate(),

    @ColumnInfo(name = "formatted_order_time")
    val formattedCheckoutTime: String? = getCurrentFormattedTime(),

    @ColumnInfo(name = "checkout_date")
    val checkoutDate: Long? = System.currentTimeMillis(),

    @ColumnInfo(name = "shipping_cost")
    val shippingCost: Double? =0.0,

    @ColumnInfo(name = "shipping_description")
    val shippingDescription: String? ="",

    @ColumnInfo(name = "payment_method")
    val paymentMethod: String? = "",

    @ColumnInfo(name = "total_price")
    val totalPrice: Double? =0.0,
) : Parcelable
    fun CheckOutEntity.toDomainCheckOut()= CheckOut(
        id = id,
        receiverName = receiverName?:"",
        receiverAddress = receiverAddress?:"",
        orderItems = orderItems.map { it.toCartDomain()  },
        shippingMethod = shippingMethod?:"",
        coupon =coupon?:"",
        formattedCheckoutDate = formattedCheckoutDate?:"",
        formattedCheckoutTime = formattedCheckoutTime?:"",
        checkoutDate = checkoutDate?:0L,
        shippingCost = shippingCost?:0.0,
        shippingDescription = shippingDescription?:"",
        paymentMethod = paymentMethod?:"",
        totalPrice = totalPrice?:0.0
    )

    fun CheckOut.toEntityCheckOut()= CheckOutEntity(
        id = id,
        receiverName = receiverName,
        receiverAddress = receiverAddress,
        orderItems = orderItems.map { it.toEntityCart() },
        shippingMethod = shippingMethod,
        coupon =coupon,
        formattedCheckoutDate = formattedCheckoutDate,
        formattedCheckoutTime = formattedCheckoutTime,
        checkoutDate = checkoutDate,
        shippingCost = shippingCost,
        shippingDescription = shippingDescription,
        paymentMethod = paymentMethod,
        totalPrice = totalPrice
    )


fun getCurrentFormattedTime(): String {
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    timeFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    return timeFormat.format(Date())
}

fun getCurrentFormattedDate(): String {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    return dateFormat.format(Date())
}

