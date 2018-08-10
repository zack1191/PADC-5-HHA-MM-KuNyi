package com.hha.heinhtetaung.mm_kunyi.mvp.presenters

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.hha.heinhtetaung.mm_kunyi.mvp.views.BaseView

/**
 * Created by E5 on 8/7/2018.
 */
abstract class BasePresenter<T : BaseView> : ViewModel() {
    lateinit var mView: T
    lateinit var context: Context
    lateinit var mErrorLd: MutableLiveData<String>
    open fun initPresenter(mView: T) {
        this.mView = mView
        mErrorLd = MutableLiveData()
    }

    fun getErrorLd(): LiveData<String> {
        return mErrorLd
    }
}