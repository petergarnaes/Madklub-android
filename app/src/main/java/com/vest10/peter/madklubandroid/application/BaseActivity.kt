package com.vest10.peter.madklubandroid.application

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by peter on 18-09-17.
 */
open class BaseActivity : AppCompatActivity() {
    protected var subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        // TODO perform check to see if we are logged in
    }

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeDisposable()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }

    fun showMessage(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}