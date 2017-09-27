package com.vest10.peter.madklubandroid.main_activity

import com.vest10.peter.madklubandroid.BaseView
import com.vest10.peter.madklubandroid.application.BasePresenter
import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ConfigPersistentScope
import javax.inject.Inject

/**
 * Created by peter on 27-09-17.
 */
@ConfigPersistentScope
class MainPresenter @Inject constructor() : BasePresenter<MainPresenter.MainView>(null) {
    override fun attachView(view: MainView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    interface MainView : BaseView {
    }
}