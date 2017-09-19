package com.vest10.peter.madklubandroid.application

import android.app.Activity
import android.app.Application
import com.vest10.peter.madklubandroid.application.di.ActivityInjectorsModule
import com.vest10.peter.madklubandroid.application.di.AppComponent
import com.vest10.peter.madklubandroid.application.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import net.danlew.android.joda.JodaTimeAndroid
import javax.inject.Inject

/**
 * Created by peter on 02-09-17.
 */
class MadklubApplication : Application(), HasActivityInjector {
    @Inject lateinit var mActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = mActivityInjector

    //@Inject protected lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    internal lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        injectDagger()
        JodaTimeAndroid.init(this)
    }

    // Not necessary to manually inject when extending the DaggerApplication class
    fun injectDagger() {
        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build()
        appComponent.inject(this)
        //appComponent.userManager.userComponent?.
    }

    /*override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }*/
}