package com.hha.heinhtetaung.mm_kunyi.adapters

import android.content.Context
import android.view.ViewGroup
import com.hha.heinhtetaung.mm_kunyi.R
import com.hha.heinhtetaung.mm_kunyi.data.vo.JobsVO
import com.hha.heinhtetaung.mm_kunyi.delegate.JobsItemDelegate
import com.hha.heinhtetaung.mm_kunyi.viewholders.BaseViewHolder
import com.hha.heinhtetaung.mm_kunyi.viewholders.JobsViewHolder

/**
 * Created by E5 on 7/31/2018.
 */
class JobsAdapter(context: Context,
                  private val mJobsItemDelegate: JobsItemDelegate) : BaseRecyclerAdapter<JobsViewHolder, JobsVO>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        val jobsItemView = mLayoutInflator.inflate(R.layout.item_jobs, parent, false)
        return JobsViewHolder(jobsItemView, mJobsItemDelegate)
    }


}