package com.vest10.peter.madklubandroid.detail_activity

import com.vest10.peter.madklubandroid.BaseView
import com.vest10.peter.madklubandroid.application.BasePresenter

/**
 * Created by peter on 27-09-17.
 */
class DetailPresenter : BasePresenter<DetailPresenter.DetailView>(null) {
    override fun attachView(view: DetailView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    interface DetailView : BaseView {

    }

}