package com.vest10.peter.madklubandroid.detail_activity

import com.vest10.peter.madklubandroid.BaseView
import com.vest10.peter.madklubandroid.application.BasePresenter
import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ConfigPersistentScope
import com.vest10.peter.madklubandroid.networking.NetworkService
import javax.inject.Inject

/**
 * Created by peter on 27-09-17.
 */
@ConfigPersistentScope
class DetailPresenter @Inject constructor(val networkService: NetworkService) : BasePresenter<DetailPresenter.DetailView>(null) {
    override fun attachView(view: DetailView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    interface DetailView : BaseView {

    }

}