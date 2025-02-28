package com.example.shopapps.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shopapps.data.local.entity.UserLocationEntity

@Dao
interface UserLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserLocation(userLocationEntity: UserLocationEntity)

    @Query("SELECT * FROM userlocationentity")
    fun getAllUserLocation() : LiveData<List<UserLocationEntity>>

    @Query("SELECT * FROM userlocationentity WHERE id=:id")
    fun getUserLocationById(id:Int) : LiveData<UserLocationEntity>

    @Query("DELETE FROM userlocationentity WHERE id=:id")
    suspend fun deleteUserLocationById(id:Int)

    @Query("DELETE FROm userlocationentity")
    suspend fun deleteAllUserLocation()
}