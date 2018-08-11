package com.hha.heinhtetaung.mm_kunyi.activities

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.hha.heinhtetaung.mm_kunyi.R
import com.hha.heinhtetaung.mm_kunyi.adapters.JobsAdapter
import com.hha.heinhtetaung.mm_kunyi.data.models.JobsModel
import com.hha.heinhtetaung.mm_kunyi.delegate.JobsItemDelegate
import com.hha.heinhtetaung.mm_kunyi.event.FirebaseEvent

import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.View
import com.google.android.gms.appinvite.AppInvite
import com.google.android.gms.appinvite.AppInviteInvitation
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.hha.heinhtetaung.mm_kunyi.KuNyiApp
import com.hha.heinhtetaung.mm_kunyi.data.vo.JobsVO
import com.hha.heinhtetaung.mm_kunyi.mvp.presenters.JobListPresenter
import com.hha.heinhtetaung.mm_kunyi.mvp.views.JobListView
import java.util.HashMap


class MainActivity : AppCompatActivity(), JobListView, NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {


    protected val RC_GOOGLE_SIGN_IN = 1236
    protected val RC_INVITE_TO_APP = 1237
    protected lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mPresenter: JobListPresenter
    private val IE_NOTIFICATION_ID = "IE_NOTIFICATION_ID"
    private val IE_LAUNCH_ACTION = "IE_LAUNCH_ACTION"
    private val LAUNCH_ACTION_TAP_SAVE_NEWS_NOTI_ACTION = 2222
    private val LAUNCH_ACTION_TAP_NOTI_BODY = 2223

    private lateinit var mJobsAdapter: JobsAdapter
    private var mFirebaseRemoteConfig: FirebaseRemoteConfig? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_24dp)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        }

        initRemoteConfig()
        fetchRemoteConfig()

        mPresenter = ViewModelProviders.of(this).get(JobListPresenter::class.java)
        mPresenter.initPresenter(this)

        rvMain.setEmptyView(vpEmpty)

        rvMain.layoutManager = LinearLayoutManager(applicationContext)
        mJobsAdapter = JobsAdapter(applicationContext, mPresenter)
        rvMain.adapter = mJobsAdapter


        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        navigationView.setNavigationItemSelectedListener(this)


        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("130670684119-jtnfpikbhol49u18c8vmgcibqgs7i3qf.apps.googleusercontent.com")
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(applicationContext)
                .enableAutoManage(this /*FragmentActivity*/, this /*OnConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .addApi(AppInvite.API)
                .build()

        val bundle = intent.extras
        if (bundle != null) {
            val notificationId = bundle.getInt(IE_NOTIFICATION_ID)
            if (notificationId != 0) {
                dismissNotification(notificationId)
            }

            val launchAction = bundle.getInt(IE_LAUNCH_ACTION)
            when (launchAction) {
                LAUNCH_ACTION_TAP_SAVE_NEWS_NOTI_ACTION -> Snackbar.make(rvMain, "\"Save News\" from notification action!", Snackbar.LENGTH_SHORT).show()
                LAUNCH_ACTION_TAP_NOTI_BODY -> Snackbar.make(rvMain, "Tapped notification body", Snackbar.LENGTH_SHORT).show()
            }
        }

        mPresenter.getJobListLd().observe(this, Observer<List<JobsVO>> { t ->
            displayJobsListData(t!!)
        })

    }


    private fun dismissNotification(notificationID: Int) {
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.cancel(notificationID)
    }

    private fun initRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val firebaseRemoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build()
        val defaultConfigMap = HashMap<String, Any>()
        defaultConfigMap.put(KuNyiApp.RC_JOBS_FEED_LAYOUT, 1)

        // Apply config settings and default values.
        mFirebaseRemoteConfig.run {
            this!!.setConfigSettings(firebaseRemoteConfigSettings)
            setDefaults(defaultConfigMap)
        }

    }

    private fun fetchRemoteConfig() {
        var cacheExpiration: Long = 3600 // 1 hour in seconds
        // If developer mode is enabled reduce cacheExpiration to 0 so that
        // each fetch goes to the server. This should not be used in release
        // builds.
        if (mFirebaseRemoteConfig!!.getInfo().configSettings
                .isDeveloperModeEnabled) {
            cacheExpiration = 0
        }
        mFirebaseRemoteConfig!!.fetch(cacheExpiration)
                .addOnSuccessListener {
                    // Make the fetched config available via
                    // FirebaseRemoteConfig get<type> calls.
                    mFirebaseRemoteConfig!!.activateFetched()
                    applyRetrievedLengthLimit()
                }
                .addOnFailureListener { e ->
                    // There has been an error fetching the config

                    applyRetrievedLengthLimit()
                }
    }

    private fun applyRetrievedLengthLimit() {
        val newsFeedLayout = mFirebaseRemoteConfig!!.getLong(KuNyiApp.RC_JOBS_FEED_LAYOUT)
        mJobsAdapter.setNewsFeedLayout(newsFeedLayout.toInt())

    }

    private fun sendInvitation() {
        val intent = AppInviteInvitation.IntentBuilder("Join MM_KuNyi")
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build()
        startActivityForResult(intent, RC_INVITE_TO_APP)
    }

    private fun displayJobsListData(jobsList: List<JobsVO>) {
        mJobsAdapter.appendNewData(jobsList)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeNavigation -> {

            }
            R.id.register -> {
                val intent = RegisterActivity.newIntent(this)
                startActivity(intent)

            }
            R.id.signInWithGoogle -> {
                if (JobsModel.getInstance().isUserSignIn()) {
                    startAddNewsActivity()
                } else {
                    Snackbar.make(rvMain, "You need to sign with Google to post Jobs!", Snackbar.LENGTH_INDEFINITE).setAction("Sign-In") { signInWithGoogle() }.show()
                }

            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return false
    }

    private fun signInWithGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            processGoogleSignInResult(result)
        } else if (requestCode == RC_INVITE_TO_APP) {
            if (resultCode == Activity.RESULT_OK) {
                // Check how many invitations were sent and log.
                val ids = AppInviteInvitation.getInvitationIds(resultCode, data!!)

                Snackbar.make(rvMain, "Invitations sent to " + ids.size + " friends", Snackbar.LENGTH_SHORT).show()
            } else {
                // Sending failed or it was canceled, show failure message to the user

                Snackbar.make(rvMain, "Failed to send invitation.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            Snackbar.make(rvMain, "Navigation", Snackbar.LENGTH_INDEFINITE)
        } else if (item.itemId == R.id.appInvite) {
            sendInvitation()

        }
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.appInvite -> true
            R.id.home -> true
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun processGoogleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount
            JobsModel.getInstance().authenticateUserWithGoogleAccount(account!!, object : JobsModel.SignInWithGoogleAccountDelegate {
                override fun onSuccessSignIn(signInAccount: GoogleSignInAccount) {
                    startAddNewsActivity()
                }

                override fun onFailureSignIn(msg: String) {

                }
            })
        } else {
            Snackbar.make(rvMain, "Google Sign-in failed.", Snackbar.LENGTH_INDEFINITE).show()
        }
    }

    private fun startAddNewsActivity() {
        Snackbar.make(rvMain, "Sign in success!", Snackbar.LENGTH_INDEFINITE).show()
        val intent = JobPostActivity.newIntent(applicationContext)
        startActivity(intent)
    }

    override fun onLaunchDetailJob(jobsId: Int) {
        val intent = JobsDetailsActivity.newIntent(applicationContext, jobsId)
        startActivity(intent)

    }


}
