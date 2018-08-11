package com.hha.heinhtetaung.mm_kunyi.fcm

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by E5 on 8/11/2018.
 */
class AddFirebaseInstanceIdService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val token = FirebaseInstanceId.getInstance().token

    }
}