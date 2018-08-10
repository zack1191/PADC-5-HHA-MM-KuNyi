package com.hha.heinhtetaung.mm_kunyi.viewholders

import android.view.View
import com.hha.heinhtetaung.mm_kunyi.data.vo.JobsVO
import com.hha.heinhtetaung.mm_kunyi.delegate.JobsItemDelegate
import kotlinx.android.synthetic.main.item_jobs.view.*

/**
 * Created by E5 on 7/31/2018.
 */
class JobsViewHolder(itemview: View,
                     private val mJobsItemDelegate: JobsItemDelegate) : BaseViewHolder<JobsVO>(itemview) {
    override fun setData(data: JobsVO) {
        mData = data
//        itemView.jobsTitle.text = data.jobTags!![0].tag
//        itemView.jobsPrice.text = data.offerAmount!!.amount.toString()
        itemView.jobsDesMain.text = data.shortDesc
//        itemView.jobsHours.text = "" + data.jobDuration!!.totalWorkingDays + "days"
//        itemView.jobsLocation.text = data.location
        itemView.tvJobPostedDate.text = data.postedDate

    }

    override fun onClick(p0: View?) {
        mJobsItemDelegate.onTapJobs(this.mData!!)
    }

}