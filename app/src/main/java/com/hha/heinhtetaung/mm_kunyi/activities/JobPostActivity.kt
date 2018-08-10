package com.hha.heinhtetaung.mm_kunyi.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.hha.heinhtetaung.mm_kunyi.R
import com.hha.heinhtetaung.mm_kunyi.data.models.JobsModel
import com.hha.heinhtetaung.mm_kunyi.data.vo.JobsVO
import com.hha.heinhtetaung.mm_kunyi.viewpods.AddedPhotoViewPod
import kotlinx.android.synthetic.main.activity_job_post.*
import kotlinx.android.synthetic.main.viewpod_added_photo.*

/**
 * Created by E5 on 8/8/2018.
 */
class JobPostActivity : AppCompatActivity() {
    private lateinit var mProgressDialog: ProgressDialog
    private var PICK_IMAGE_REQUEST = 1
    private lateinit var jobsVO: MutableLiveData<List<JobsVO>>

    private lateinit var vpAddedPhoto: AddedPhotoViewPod
    private var mPhotoUrl = ""


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, JobPostActivity::class.java)
        }
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_post)
        setSupportActionBar(toolbarJobPost)

        if (supportActionBar != null) {
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }


        vpAddPhoto.setOnClickListener {
            val intent = Intent()
            // Show only images, no videos or anything else
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            // Always show the chooser (if there are multiple options available)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)

        }

        btnJobPost.setOnClickListener {
            val jobsContent = etJobPost.text.toString()
            if (TextUtils.isEmpty(jobsContent)) {
                etJobPost.error = "Need a short description for Job post"

            } else {
                showProgressDialogInfinite("Posting Your Job")

                JobsModel.getInstance().addJob(jobsContent)
                dismissProgressDialog()
                JobsModel.getInstance().loadJobs(jobsVO)
                onBackPressed()

            }

//            when {
//                TextUtils.isEmpty(jobsContent) -> etJobPost.error = "Need news content to publish."
//                TextUtils.isEmpty(vpAddedPhoto.getPhotoUrl()) -> Snackbar.make(etJobPost, "You should select a photo relating to the news.", Snackbar.LENGTH_LONG).show()
//                else -> {
//                    showProgressDialogInfinite("Publishing your news")
//                    JobsModel.getInstance().uploadFile(vpAddedPhoto.getPhotoUrl(), object : JobsModel.UploadFileCallBack {
//                        override fun onUploadSucceeded(uploadedPaths: String) {
//                            dismissProgressDialog()
//                            JobsModel.getInstance().addJob(jobsContent, uploadedPaths)
//                            onBackPressed()
//                        }
//
//                        override fun onUploadFailed(msg: String) {
//                            Snackbar.make(etJobPost, "Your photo couldn't be uploaded because : " + msg, Snackbar.LENGTH_LONG).show()
//                        }
//                    })
//                }
//            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == Activity.RESULT_OK && data != null
                && data.data != null) {
            val uri = data.data
            onPhotoTaken(uri!!.toString())
            //mTakenPhotosAdapter.addNewData(uri.toString());
        }
    }

    fun setImageView(photoUrl: String) {
        mPhotoUrl = photoUrl
        Glide.with(this.applicationContext)
                .load(photoUrl)
                .centerCrop()
                .placeholder(R.drawable.img_take_photo)
                .error(R.drawable.img_take_photo)
                .into(ivAddedPhoto)
    }

    fun onPhotoTaken(photo: String) {
        if (TextUtils.isEmpty(photo)) {
            Snackbar.make(etJobPost, "ERROR : Path to photo is empty.", Snackbar.LENGTH_LONG).show()
        } else {
            setImageView(photo)
            vpAddPhoto.visibility = View.GONE
            vpAddedPhoto.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            if (item.itemId == android.R.id.home) {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showProgressDialogInfinite(msg: String) {
        //if (mProgressDialog == null) {
        mProgressDialog = ProgressDialog(this, R.style.AppDialog)
        //}

        mProgressDialog.setMessage(Html.fromHtml(msg))


        if (!mProgressDialog.isShowing) {
            mProgressDialog.setCancelable(false)
            mProgressDialog.show()
        }
    }


    fun dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }

}