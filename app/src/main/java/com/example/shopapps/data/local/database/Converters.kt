package com.example.shopapps.data.local.database

import androidx.room.TypeConverter
import com.example.shopapps.data.local.entity.CartEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromCartList(value: List<CartEntity>): String {
        val gson = Gson().toJson(value)
        return gson
    }

    @TypeConverter
    fun toCartList(value: String): List<CartEntity> {
        val gson = Gson()
        val stringType = object : TypeToken<List<CartEntity>>() {}.type
        return gson.fromJson(value, stringType)

    }
}