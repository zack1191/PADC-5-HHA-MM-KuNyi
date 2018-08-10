package com.hha.heinhtetaung.mm_kunyi.activities

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.hha.heinhtetaung.mm_kunyi.KuNyiApp
import com.hha.heinhtetaung.mm_kunyi.R
import com.hha.heinhtetaung.mm_kunyi.R.id.*
import com.hha.heinhtetaung.mm_kunyi.data.vo.JobsVO
import com.hha.heinhtetaung.mm_kunyi.mvp.presenters.JobDetailPresenter
import com.hha.heinhtetaung.mm_kunyi.mvp.views.JobDetailView
import kotlinx.android.synthetic.main.activity_jobs_details.*

/**
 * Created by E5 on 7/31/2018.
 */
class JobsDetailsActivity : AppCompatActivity(), JobDetailView {
    private lateinit var mPresenter: JobDetailPresenter

    companion object {
        fun newIntent(context: Context, JobsId: Int): Intent {
            return Intent(context, JobsDetailsActivity::class.java).putExtra(KuNyiApp.JOBS_ID, JobsId)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs_details)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        mPresenter = ViewModelProviders.of(this).get(JobDetailPresenter::class.java)
        mPresenter.initPresenter(this)

        val jobId: Int = intent.getIntExtra(KuNyiApp.JOBS_ID, -1)
        mPresenter.getJobById(jobId).observe(this, Observer<JobsVO> { t ->
            displayJobDetails(t!!)
        })

    }

    @SuppressLint("SetTextI18n")
    private fun displayJobDetails(jobsVO: JobsVO) {
        jobTitle.text = jobsVO.jobTags!![0].tag
        jobsLocation.text = jobsVO.location
        jobPayment.text = "" + jobsVO.offerAmount!!.amount + "/month"
        jobPeriod.text = jobsVO.jobDuration!!.jobStartDate + " to " + jobsVO.jobDuration!!.jobEndDate
        jobVacancy.text = jobsVO.availablePostCount.toString()
        jobDesc.text = jobsVO.fullDesc

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            if (item.itemId == android.R.id.home) {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}