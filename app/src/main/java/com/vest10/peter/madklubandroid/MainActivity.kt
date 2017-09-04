package com.vest10.peter.madklubandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vest10.peter.madklubandroid.networking.NetworkService
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var network : NetworkService
    @Inject lateinit var someClass : SomeClass

    // How to do named
    //@Inject @field:Named("users") lateinit var mFirebaseUsers: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Injected network",network.toString())
        Log.d("Injected somestring",someClass.toString())
    }
}
