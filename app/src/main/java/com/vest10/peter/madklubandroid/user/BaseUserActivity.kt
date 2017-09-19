package com.vest10.peter.madklubandroid.user

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.vest10.peter.madklubandroid.application.MadklubApplication
import com.vest10.peter.madklubandroid.depenedency_injection.components.UserComponent
import javax.inject.Inject


/**
 * Created by peter on 18-09-17.
 */
abstract class BaseUserActivity : AppCompatActivity() {
    // Injected by BaseActivity
    @Inject lateinit var userManager: AppUserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // To inject userManager
        (application as MadklubApplication).appComponent.inject(this)
        // Tries to set userManager.userComponent
        userManager.attemptToLoadUser()
        // Safely calls setupUserComponent
        userManager.userComponent?.let { setupUserComponent(it) }
        // If for some reason we are not logged in, it means that the user was logged out
        // while
        if (!userManager.isLoggedIn){
            Log.d("Madklub","User not logged in somehow...")
            // TODO When we change the launcher activity to main, this should instead go to login
            val i = baseContext.packageManager
                    .getLaunchIntentForPackage(baseContext.packageName)
            // TODO check that FLAG_ACTIVITY_CLEAR_TOP together with finish() actually
            // clears the whole activity stack. Important since we are throwing them back
            // to the login screen
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
            startActivity(i)
            //val mPendingIntentId = 123456
            //val mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId, i,
            //        PendingIntent.FLAG_CANCEL_CURRENT)
            //val mgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            //mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent)
            //System.exit(0)
        } else {
            onCreateSafe(savedInstanceState)
        }
    }

    protected fun logoutUser(){
        userManager.logOut()
        // TODO go to login activity
    }

    abstract fun setupUserComponent(userComponent: UserComponent)

    abstract fun onCreateSafe(savedInstanceState: Bundle?)
}