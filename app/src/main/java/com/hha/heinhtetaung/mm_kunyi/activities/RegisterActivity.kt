package com.hha.heinhtetaung.mm_kunyi.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.hha.heinhtetaung.mm_kunyi.R
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Created by E5 on 8/3/2018.
 */
class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(registerToolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        }

    }

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, RegisterActivity::class.java)
            return intent
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
}