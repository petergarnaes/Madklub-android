package com.vest10.peter.madklubandroid.user

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.vest10.peter.madklubandroid.application.BaseActivity
import com.vest10.peter.madklubandroid.application.MadklubApplication
import com.vest10.peter.madklubandroid.depenedency_injection.components.UserComponent
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by peter on 18-09-17.
 */
abstract class BaseUserActivity : AppCompatActivity() {
    // Injected by BaseActivity
    @Inject lateinit var userManager: AppUserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MadklubApplication).appComponent.inject(this)
        val userComponent = userManager.userComponent
        if(userComponent != null) {
            Log.d("Madklub","UserComponent exists, injecting user activity...")
            setupUserComponent(userComponent)
        }
        //userManager.userComponent?.let {  inject(it) }
        if (!userManager.isLoggedIn){
            Log.d("Madklub","User not logged in somehow...")
            // TODO we are not logged in, go to login screen!
        }
    }

    protected fun logoutUser(){
        userManager.logOut()
        // TODO go to login activity
    }

    abstract fun setupUserComponent(userComponent: UserComponent)
}