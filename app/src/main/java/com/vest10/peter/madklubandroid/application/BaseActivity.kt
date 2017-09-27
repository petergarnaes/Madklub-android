package com.vest10.peter.madklubandroid.application

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.vest10.peter.madklubandroid.BaseView
import com.vest10.peter.madklubandroid.authentication.MadklubUserManager
import com.vest10.peter.madklubandroid.depenedency_injection.components.ConfigPersistentComponent
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

/**
 * Created by peter on 18-09-17.
 */
abstract class BaseActivity<T : BaseView,P : BasePresenter<T>> : AppCompatActivity() {
    companion object {
        // For tracking ConfigPersistentComponent's across configuration changes
        val KEY_ACTIVITY_ID = "keyActivityID"
        val NEXT_ID = AtomicLong(1)
        val configComponentMap = HashMap<Long,ConfigPersistentComponent>()
    }

    protected var disposables = CompositeDisposable()
    var activityID: Long = 0
    @Inject
    lateinit var userManager: MadklubUserManager
    @Inject
    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        //AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        // Doing persistence
        activityID = savedInstanceState?.getLong(KEY_ACTIVITY_ID) ?: NEXT_ID.getAndIncrement()
        val configComponent = if(!configComponentMap.containsKey(activityID)){
            val configComponent = (application as MadklubApplication).appComponent.plus()
            configComponentMap[activityID] = configComponent
            configComponent
        } else {
            Log.d("MadklubPersistence","Reusing component")
            configComponentMap[activityID]!!
        }
        injectMembers(configComponent)

        // Setting up presenter
        presenter.attachView(this as T)


        // Checking if authenticated
        val d = userManager.setupAuthentication(this).subscribe {
            launchAuthenticatedNetworkRequests()
        }
        disposables.add(d)
    }

    abstract fun injectMembers(configPersistentComponent: ConfigPersistentComponent)

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putLong(KEY_ACTIVITY_ID,activityID)
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MadklubLifeCycle","OnDestroy!!")
        // Ensures UI changes are never attempted in situations where view is destroyed
        presenter.detachView()
        // If the activity is being destroyed for good
        if(!isChangingConfigurations){
            presenter.unsubscribe()
            configComponentMap.remove(activityID)
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}