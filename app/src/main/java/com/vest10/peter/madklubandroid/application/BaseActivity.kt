package com.vest10.peter.madklubandroid.application

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.vest10.peter.madklubandroid.authentication.MadklubUserManager
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by peter on 18-09-17.
 */
abstract class BaseActivity : AppCompatActivity() {
    protected var disposables = CompositeDisposable()
    @Inject
    lateinit var userManager: MadklubUserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        // TODO perform check to see if we are logged in
        val d = userManager.setupAuthentication(this).subscribe {
            launchAuthenticatedNetworkRequests()
        }
        disposables.add(d)
    }

    /**
     * Should be used for authenticated network calls, since this function is
     * called only after the user has been set up.
     * */
    abstract fun launchAuthenticatedNetworkRequests()

    override fun onResume() {
        super.onResume()
        disposables = CompositeDisposable()
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

    fun showMessage(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}