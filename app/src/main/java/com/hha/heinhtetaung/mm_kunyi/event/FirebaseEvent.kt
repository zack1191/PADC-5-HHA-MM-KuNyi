package com.hha.heinhtetaung.mm_kunyi.event

import com.hha.heinhtetaung.mm_kunyi.data.vo.JobsVO

/**
 * Created by E5 on 8/2/2018.
 */
class FirebaseEvent {

    companion object
    class JobsLoadedEvent {
        private lateinit var job: List<JobsVO>

        fun JobsLoaded(jobs: List<JobsVO>) {
            this.job = jobs
        }

        fun GetJobs(): List<JobsVO> {
            return job
        }
    }


}