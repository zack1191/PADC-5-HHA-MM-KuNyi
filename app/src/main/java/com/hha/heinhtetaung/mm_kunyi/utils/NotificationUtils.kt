package com.hha.heinhtetaung.mm_kunyi.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Bitmap
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.app.TaskStackBuilder
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.hha.heinhtetaung.mm_kunyi.R
import com.hha.heinhtetaung.mm_kunyi.activities.MainActivity
import java.util.concurrent.ExecutionException

/**
 * Created by E5 on 8/11/2018.
 */
class NotificationUtils {


    private val REQUEST_ID_SAVE_NEWS = 3001

    val KEY_MESSAGE = "custom_msg"

    companion object {
        private val NOTIFICATION_ID_NEW_MESSAGE = 2001
        val KEY_MESSAGE = "custom_msg"

        fun notifyCustomMsg(context: Context, message: String) {
            //Notification Title
            val title = context.getString(R.string.app_name)

            //Supporting both unicode & zawgyi
            val mmMessage = message

            //Large Icon
            //val appIcon = encodeResourceToBitmap(context, R.mipmap.ic_news_from_people)

            //Message in BigText Style
            val bigTextStyle = NotificationCompat.BigTextStyle()
            bigTextStyle.bigText(mmMessage)

            val builder = NotificationCompat.Builder(context)
                    .setColor(context.resources.getColor(R.color.accent))
                    .setSmallIcon(R.drawable.ic_share_24dp)
                    // .setLargeIcon(appIcon)
                    .setContentTitle(title)
                    .setContentText(mmMessage)
                    .setAutoCancel(true)
                    .setStyle(bigTextStyle)

            //Notification action to Play Songs by Artist.
            // saveNewsAction(context, NOTIFICATION_ID_NEW_MESSAGE, builder)

            //Open the app when user tap on notification
            //val resultIntent = NewsFeedActivity.newIntentNotiBody(context)

            //val stackBuilder = TaskStackBuilder.create(context)
            //stackBuilder.addNextIntent(resultIntent)
            //val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            //builder.setContentIntent(resultPendingIntent)

            val notificationManager = NotificationManagerCompat.from(context)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "MMKuuNyii"
                val channel = NotificationChannel(channelId, title, NotificationManager.IMPORTANCE_DEFAULT)
                channel.description = message
                val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
                builder.setChannelId(channelId)
            }
            notificationManager.notify(NOTIFICATION_ID_NEW_MESSAGE, builder.build())
        }

    }

    private fun encodeResourceToBitmap(context: Context, resourceId: Int): Bitmap? {
        var bitmap: Bitmap? = null
        //Encode bitmap for large notification icon
        val largeIconWidth = context.resources.getDimensionPixelSize(android.R.dimen.notification_large_icon_width)
        val largeIconHeight = context.resources.getDimensionPixelSize(android.R.dimen.notification_large_icon_height)

        try {
            bitmap = Glide.with(context)
                    .load(resourceId)
                    .asBitmap()
                    .into(largeIconWidth, largeIconHeight)
                    .get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

        return bitmap
    }

    private fun saveNewsAction(context: Context, notificationId: Int, builder: NotificationCompat.Builder) {
        //Intent to execute when user tap on Action Button.
        // val saveNewsActionIntent = MainActivity.newIntentSaveNews(context, notificationId)
        //val playSongsByArtistActionPendingIntent = PendingIntent.getActivity(context, REQUEST_ID_SAVE_NEWS, saveNewsActionIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        //"Save News" Action Label.
        //val notiActionSaveNews = context.getString(R.string.noti_action_save_news)

        //Action Button Icon.
        //val actionIcon = R.drawable.ic_bookmark_border_24dp
        //if (TextUtils.equals(Build.BRAND, FirebaseAppConstants.VENDOR_XIAOMI)) {
        //actionIcon = R.drawable.ic_other_bookmark_border_24dp;
        //}

        //val playSongsByArtistAction = NotificationCompat.Action(actionIcon,
        //      notiActionSaveNews, playSongsByArtistActionPendingIntent)
        //builder.addAction(playSongsByArtistAction)
    }
}