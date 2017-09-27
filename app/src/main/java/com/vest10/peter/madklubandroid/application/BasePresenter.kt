package com.vest10.peter.madklubandroid.application

import android.app.Activity
import android.util.Log
import com.vest10.peter.madklubandroid.BaseView
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by peter on 27-09-17.
 */
abstract class BasePresenter<V : BaseView>(var view: V?) {
    init {
        Log.d("MadklubPresenter","Am I being initialized a lot?")
    }
    protected var configPersistentDisposables = CompositeDisposable()
    protected var uiDependentDisposables = CompositeDisposable()

    abstract fun attachView(view: V)
    abstract fun detachView()
}
