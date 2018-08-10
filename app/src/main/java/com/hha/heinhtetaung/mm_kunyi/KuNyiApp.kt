package com.hha.heinhtetaung.mm_kunyi

import android.app.Application
import com.hha.heinhtetaung.mm_kunyi.data.models.JobsModel
import com.hha.heinhtetaung.mm_kunyi.event.FirebaseEvent

/**
 * Created by E5 on 8/2/2018.
 */
class KuNyiApp : Application() {

    companion object {
        val FIRE_BASE_CHILD: String = "kunyijobs"
        val JOBS_ID: String = "JOBS_ID"
        val FIRE_BASE_STORAGE: String = "gs://hha-mm-kunyi.appspot.com"

    }

    override fun onCreate() {
        super.onCreate()
        JobsModel.initJobsModel(applicationContext)
    }
}