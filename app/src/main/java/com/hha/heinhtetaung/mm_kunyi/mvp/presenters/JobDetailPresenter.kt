package com.hha.heinhtetaung.mm_kunyi.mvp.presenters

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.hha.heinhtetaung.mm_kunyi.data.models.JobsModel
import com.hha.heinhtetaung.mm_kunyi.data.vo.JobsVO
import com.hha.heinhtetaung.mm_kunyi.mvp.views.JobDetailView

/**
 * Created by E5 on 8/7/2018.
 */
class JobDetailPresenter : BasePresenter<JobDetailView>() {
    private lateinit var jobsIdLd: MutableLiveData<JobsVO>
    override fun initPresenter(mView: JobDetailView) {
        super.initPresenter(mView)
        jobsIdLd = MutableLiveData()
    }

    fun getJobById(jobsId: Int): LiveData<JobsVO> {
        var jobs: JobsVO = JobsModel.getInstance().getJobsId(jobsId)!!
        jobsIdLd.value = jobs
        return jobsIdLd
    }

}