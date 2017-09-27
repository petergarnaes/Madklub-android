package com.vest10.peter.madklubandroid.main_activity

import android.util.Log
import com.vest10.peter.madklubandroid.BaseView
import com.vest10.peter.madklubandroid.application.BaseContract
import com.vest10.peter.madklubandroid.application.BasePresenter

/**
 * Created by peter on 27-09-17.
 */
class MainPresenter : BasePresenter<MainPresenter.MainView>(null) {
    override fun attachView(view: MainView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    interface MainView : BaseView {
    }
}