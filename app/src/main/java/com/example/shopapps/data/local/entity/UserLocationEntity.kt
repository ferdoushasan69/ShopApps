package com.example.shopapps.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shopapps.domain.model.UserLocation
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class UserLocationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "address")
    val address: String,
) : Parcelable
    fun UserLocationEntity.toDomainUserLocation()= UserLocation(
        id = id,
        name = name,
        address = address
    )

    fun UserLocation.toEntityUserLocation()= UserLocationEntity(
        id = id,
        name = name,
        address = address
    )
