package com.example.shopapps.presentation.notification

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.core.app.NotificationCompat
import android.graphics.Bitmap
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.toBitmap
import com.example.shopapps.R
import com.example.shopapps.presentation.utils.DialogHelper
import okhttp3.internal.notify
import javax.inject.Inject
import kotlin.random.Random

class NotificationService @Inject constructor(
    private val context: Context
) {
    val notificationManager = context.getSystemService(NotificationManager::class.java)

    suspend fun getBitmapFromUrl(context: Context, url: String): Bitmap? {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false) // important: disable hardware bitmaps for notifications
            .build()

        val result = loader.execute(request)
        return  if (result is SuccessResult){
            result.image.toBitmap()
        }else {null}
        return (result.image as? BitmapDrawable)?.bitmap
    }
    @SuppressLint("SuspiciousIndentation")
    suspend fun showExpandableNotification(context: Context, imageUrl: String, title : String, contentText : String){

        val image = getBitmapFromUrl(context, url = imageUrl)
        val notification = NotificationCompat.Builder(context, DialogHelper.NOTIFICATION_ID)
            .setContentTitle(title)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLargeIcon(image)
            .setStyle(
                NotificationCompat
                    .BigPictureStyle()
                    .bigPicture(image)
                    .bigLargeIcon(null as android.graphics.Bitmap?)
            )
            .setAutoCancel(true)
            .build()
           notificationManager.notify(
                Random.nextInt(),
               notification

            )

    }


}