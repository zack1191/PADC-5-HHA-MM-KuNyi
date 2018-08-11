package com.hha.heinhtetaung.mm_kunyi.fcm

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hha.heinhtetaung.mm_kunyi.utils.NotificationUtils

/**
 * Created by E5 on 8/11/2018.
 */
class AddFirebaseMessagingServices : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // Handle data payload of FCM messages.

        val remoteMsgData = remoteMessage!!.data
        val message = remoteMsgData[NotificationUtils.KEY_MESSAGE]

        NotificationUtils.notifyCustomMsg(applicationContext, message.toString())
    }
}