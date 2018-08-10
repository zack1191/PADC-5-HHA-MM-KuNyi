package com.hha.heinhtetaung.mm_kunyi.data.models

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.hha.heinhtetaung.mm_kunyi.KuNyiApp
import com.hha.heinhtetaung.mm_kunyi.data.vo.JobsVO
import java.util.ArrayList

/**
 * Created by E5 on 8/2/2018.
 */
class JobsModel(context: Context) : BaseModel(context) {
    private var mDatabaseReference: DatabaseReference? = null
    private var mJobsDr: DatabaseReference? = null
    private val MM_Jobs = "kunyijobs"
    var jobLists = ArrayList<JobsVO>()
    private var mFirebaseAuth: FirebaseAuth
    private var mFirebaseUser: FirebaseUser? = null
    private val UPLOAD_IMAGE_PATH = "images"


    init {
        mDatabaseReference = FirebaseDatabase.getInstance().reference
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseUser = mFirebaseAuth.currentUser


    }

    companion object {
        private var INSTANCE: JobsModel? = null

        fun getInstance(): JobsModel {
            if (INSTANCE == null) {
                throw RuntimeException("HealthcareModel is being invoked before initializing.")
            }

            val i = INSTANCE
            return i!!
        }

        fun initJobsModel(context: Context) {
            INSTANCE = JobsModel(context)
        }
    }

    fun isUserSignIn(): Boolean {
        return mFirebaseUser != null
    }

    fun authenticateUserWithGoogleAccount(googleSignInAccount: GoogleSignInAccount, delegate: SignInWithGoogleAccountDelegate) {
        val credential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {

                        delegate.onFailureSignIn(task.exception!!.message!!)
                    } else {

                        delegate.onSuccessSignIn(googleSignInAccount)
                    }
                }
                .addOnFailureListener { e -> delegate.onFailureSignIn(e.message!!) }

    }


    fun loadJobs(mJobListLd: MutableLiveData<List<JobsVO>>) {

        mJobsDr = mDatabaseReference?.child(KuNyiApp.FIRE_BASE_CHILD)
        mJobsDr?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                    dataSnapshot.children.forEach { snapShot: DataSnapshot ->
                        var jobs: JobsVO = snapShot.getValue(JobsVO::class.java)!!
                        jobLists.add(jobs)
                    }
                    mJobListLd.value = jobLists
                }
            }

        })

    }

    fun getJobsId(jobsId: Int): JobsVO? {
        if (jobLists != null) {
            jobLists
                    .filter { it.jobPostId!! == jobsId }
                    .forEach { return it }
        }
        return null

    }

    interface SignInWithGoogleAccountDelegate {
        fun onSuccessSignIn(signInAccount: GoogleSignInAccount)

        fun onFailureSignIn(msg: String)
    }

    fun uploadFile(fileToUpload: String, callBack: UploadFileCallBack) {
        val file = Uri.parse(fileToUpload)
        val firebaseStorage = FirebaseStorage.getInstance()
        val storageRefrence = firebaseStorage.getReferenceFromUrl(KuNyiApp.FIRE_BASE_STORAGE)


        val uploadFileRef = storageRefrence.child(UPLOAD_IMAGE_PATH + "/" + file.lastPathSegment)
        val uploadTask = uploadFileRef.putFile(file)
        uploadTask.addOnFailureListener { e -> callBack.onUploadFailed(e.message!!) }.addOnSuccessListener { taskSnapshot ->
            val uploadedImageUrl = taskSnapshot.downloadUrl

            callBack.onUploadSucceeded(uploadedImageUrl!!.toString())
        }

    }

    fun addJob(shortDesc: String?) {
        val job = JobsVO.iniJobs(shortDesc!!)
        mJobsDr!!.child(job.postedDate).setValue(job)

    }

    interface UploadFileCallBack {
        fun onUploadSucceeded(uploadedPaths: String)
        fun onUploadFailed(msg: String)

    }


}