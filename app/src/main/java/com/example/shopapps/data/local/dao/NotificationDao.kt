package com.example.shopapps.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shopapps.data.local.entity.NotificationEntity

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("SELECT * FROM notificationentity ORDER by id DESC")
    fun getAllNotification(): LiveData<List<NotificationEntity>>

    @Query("UPDATE notificationentity SET isRead=1 WHERE id=:notificationId")
    suspend fun markAsRead(notificationId: Int)

    @Query("DELETE FROM notificationentity")
    suspend fun deleteAllNotification()

    @Query("SELECT COUNT(*) FROM notificationentity WHERE isRead=0")
    fun getUnReadNotificationCount(): LiveData<Int>
}