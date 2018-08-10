package com.hha.heinhtetaung.mm_kunyi.data.vo

import com.google.gson.annotations.SerializedName

/**
 * Created by E5 on 8/2/2018.
 */
 class HireVO {
    @SerializedName("msg")
    var msg: String? = null

    @SerializedName("timestamp")
    var timestamp: String? = null

    @SerializedName("whyShouldHireId")
    var hireId: Int? = null
}