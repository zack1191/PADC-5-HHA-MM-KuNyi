package com.hha.heinhtetaung.mm_kunyi.data.vo

import com.google.gson.annotations.SerializedName

/**
 * Created by E5 on 8/2/2018.
 */
 class JobsDurationVO {
     @SerializedName("jobEndDate")
     var jobEndDate: String? = null

     @SerializedName("workingDaysPerWeek")
     var workingDaysPerWeek: Int? = null

     @SerializedName("totalWorkingDays")
     var totalWorkingDays: Int? = null

     @SerializedName("workingHoursPerDay")
     var workingHoursPerDay: Int? = null

     @SerializedName("jobStartDate")
     var jobStartDate: String? = null
}