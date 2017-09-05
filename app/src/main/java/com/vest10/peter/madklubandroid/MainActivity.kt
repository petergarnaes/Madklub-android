package com.vest10.peter.madklubandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.vest10.peter.madklubandroid.networking.NetworkService
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject lateinit var network : NetworkService
    @Inject lateinit var someClass : SomeClass

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Injected network",network.toString())
        Log.d("Injected somestring",someClass.toString())
        Log.d("Injected lol string",lolString)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
}
