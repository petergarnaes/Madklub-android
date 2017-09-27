package com.vest10.peter.madklubandroid.detail_activity

import android.util.Log
import com.vest10.peter.madklubandroid.BaseView
import com.vest10.peter.madklubandroid.application.BasePresenter
import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ConfigPersistentScope
import com.vest10.peter.madklubandroid.networking.NetworkService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

/**
 * Created by peter on 27-09-17.
 */
@ConfigPersistentScope
class DetailPresenter @Inject constructor(val networkService: NetworkService) : BasePresenter<DetailPresenter.DetailView>(null) {
    var dinnerclub: DetailedDinnerclub? = null
    var dinnerclubObservable: Observable<DetailedDinnerclub>? = null
    // TODO implement proper isParticipating
    var c = true

    override fun attachView(view: DetailView) {
        this.view = view

        dinnerclub?.let(this::showDinnerclub)
        dinnerclubObservable?.let(this::subscribeToDinnerclub)
    }

    override fun detachView() {
        view = null
    }

    fun getDinnerclub(id: String){
        networkService.query {
            DinnerclubDetailQuery.builder()
                    .id(id)
                    .build()
        }.map{
            if (it.hasErrors())
                throw RuntimeException("Something wrong with query or server...")
            it.data()?.me()?.kitchen()?.dinnerclub()
        }.map {
            val cook = DetailedCook(it.cook().id() ?: "Bob",it.cook().display_name() ?: "Henny")
            DetailedDinnerclub(
                    it.id(),
                    it.at(),
                    it.cancelled(),
                    it.shopping_complete(),
                    it.total_cost(),
                    it.meal(),
                    cook,
                    // TODO implement proper participants
                    emptyList())
        }.observeOn(
                // Observing on main thread so UI updates happen (for example actionBar change
                AndroidSchedulers.mainThread()
        ).apply{
            subscribeToDinnerclub(this)
        }
    }

    fun subscribeToDinnerclub(observable: Observable<DetailedDinnerclub>){
        dinnerclubObservable = observable
        view?.let{
            observable.subscribe ({
                dinnerclub ->
                showDinnerclub(dinnerclub)
            },{
                errors ->
                // TODO make appropriate toast for error
            }).addTo(uiDependentDisposables)
        }
    }

    fun showDinnerclub(dinnerclub: DetailedDinnerclub){
        this.dinnerclub = dinnerclub
        view?.showDinnerclub(dinnerclub)
    }

    fun cancelIconClicked(){
        dinnerclub?.let { d ->
            d.cancelled = !d.cancelled
            view?.let {
                it.setCancelledIcon(d.cancelled)
            }
        }
    }

    fun participationIconClicked(){
        dinnerclub?.let {
            c = !c
            view?.let {
                it.setParticipatingIcon(c)
            }
        }
    }

    fun shoppingIconClicked(){
        dinnerclub?.let { d ->
            d.shoppingComplete = !d.shoppingComplete
            view?.let {
                it.setShoppingIcon(d.shoppingComplete)
            }
        }
    }

    interface DetailView : BaseView {
        fun showDinnerclub(dinnerclub: DetailedDinnerclub)
        fun setCancelledIcon(value: Boolean)
        fun setParticipatingIcon(value: Boolean)
        fun setShoppingIcon(value: Boolean)
    }

}