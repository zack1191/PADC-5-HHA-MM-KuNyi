package com.hha.heinhtetaung.mm_kunyi.mvp.presenters

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.hha.heinhtetaung.mm_kunyi.data.models.JobsModel
import com.hha.heinhtetaung.mm_kunyi.data.vo.JobsVO
import com.hha.heinhtetaung.mm_kunyi.delegate.JobsItemDelegate
import com.hha.heinhtetaung.mm_kunyi.mvp.views.JobListView

/**
 * Created by E5 on 8/7/2018.
 */
class JobListPresenter : BasePresenter<JobListView>(), JobsItemDelegate {
    override fun onTapJobs(jobsVO: JobsVO) {
        mView.onLaunchDetailJob(jobsVO.jobPostId!!)
    }


    private lateinit var joblistLd: MutableLiveData<List<JobsVO>>

    override fun initPresenter(mView: JobListView) {
        super.initPresenter(mView)
        joblistLd = MutableLiveData()
        JobsModel.getInstance().loadJobs(joblistLd)
    }

    fun getJobListLd(): LiveData<List<JobsVO>> {
        return joblistLd
    }
}