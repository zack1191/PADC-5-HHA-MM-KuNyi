package com.hha.heinhtetaung.mm_kunyi.components

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.viewpod_empty.view.*

/**
 * Created by E5 on 8/3/2018.
 */

class EmptyViewPod : RelativeLayout {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun setEmptyData(emptyImageId: Int, emptyMsg: String) {
        ivEmpty!!.setImageResource(emptyImageId)
        tvRefreshEmpty!!.text = emptyMsg
    }

    fun setEmptyData(emptyMsg: String) {
        tvRefreshEmpty!!.text = emptyMsg
    }
}