package com.hha.heinhtetaung.mm_kunyi.data.vo

import com.google.gson.annotations.SerializedName

/**
 * Created by E5 on 8/2/2018.
 */
 class JobTagsVO {
   @SerializedName("JobCountWithTag")
   var jobCountWithTag: Int? = null

   @SerializedName("desc")
   var desc: String? = null

   @SerializedName("tag")
   var tag: String? = null

   @SerializedName("tagId")
   var tagId: Int? = null
}