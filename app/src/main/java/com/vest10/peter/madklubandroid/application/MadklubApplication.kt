package com.vest10.peter.madklubandroid.application

import android.app.Activity
import android.app.Application
import com.vest10.peter.madklubandroid.application.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by peter on 02-09-17.
 */
open class MadklubApplication : DaggerApplication() {
    @Inject protected lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        injectDagger()
    }

    open fun injectDagger() {
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}