package com.hha.heinhtetaung.mm_kunyi.data.vo

import com.google.gson.annotations.SerializedName

/**
 * Created by E5 on 8/2/2018.
 */
class ApplicantVO {
    @SerializedName("appliedDate")
    var appliedDate: String? = null

    @SerializedName("canLowerOfferAmount")
    var lowerOfferAmount: Boolean? = null

    @SerializedName("seekerSkill")
    var seekerSkill: List<SeekerSkillVO>? = null

    @SerializedName("seekerId")
    var seekerId: Int? = null

    @SerializedName("seekerName")
    var seekerName: String? = null

    @SerializedName("whyShouldHire")
    var hire: List<HireVO>? = null

    @SerializedName("seekerProfilePicUrl")
    var seekerProfilePicUrl: String? = null




}