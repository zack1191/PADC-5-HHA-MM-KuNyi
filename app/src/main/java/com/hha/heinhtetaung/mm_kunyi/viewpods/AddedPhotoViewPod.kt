package com.hha.heinhtetaung.mm_kunyi.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.hha.heinhtetaung.mm_kunyi.R
import kotlinx.android.synthetic.main.viewpod_added_photo.view.*

/**
 * Created by E5 on 8/10/2018.
 */
class AddedPhotoViewPod : FrameLayout {

    private var mPhotoUrl: String? = null


    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()

    }

//    public fun setData(photoUrl: String) {
//
//        mPhotoUrl = photoUrl
//        Glide.with(context)
//                .load(photoUrl)
//                .centerCrop()
//                .placeholder(R.drawable.img_take_photo)
//                .error(R.drawable.img_take_photo)
//                .into(ivAddedPhoto)
//
//    }

    fun getPhotoUrl(): String {
        return mPhotoUrl.toString()
    }


}