package com.vest10.peter.madklubandroid.main_activity

import android.util.Log
import com.apollographql.apollo.exception.ApolloNetworkException
import com.vest10.peter.madklubandroid.BaseView
import com.vest10.peter.madklubandroid.application.BasePresenter
import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ConfigPersistentScope
import com.vest10.peter.madklubandroid.networking.NetworkService
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.CookDinnerclubItem
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.RegularDinnerclubItem
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.UpcommingDinnerclubItem
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

/**
 * Created by peter on 27-09-17.
 */
@ConfigPersistentScope
class MainPresenter @Inject constructor(val networkService: NetworkService) : BasePresenter<MainPresenter.MainView>(null) {
    var dinnerclubs: List<UpcommingDinnerclubItem>? = null
    var dinnerclubsObservable: Observable<List<UpcommingDinnerclubItem>>? = null

    override fun attachView(view: MainView) {
        this.view = view

        dinnerclubs?.let(this::showDinnerclubs)
        dinnerclubsObservable?.let(this::subscribeToDinnerclubs)
    }

    override fun detachView() {
        view = null
    }

    fun getDinnerclubs(){
        networkService.query {
            UpcommingDinnerclubsQuery.builder()
                    .startDate("2017-09-22T12:00:00.000Z")
                    .endDate("2017-12-22T12:00:00.000Z")
                    .build()
        }.map {
            val me = it.data()?.me()
            Pair<String,List<UpcommingDinnerclubsQuery.Dinnerclub>>(me?.id()!!,me.kitchen()?.dinnerclubs()!!)
        }.onErrorReturn({
            Log.d("We had the error", it.localizedMessage)
            Log.d("We had the error", "$it")
            Pair("",emptyList<UpcommingDinnerclubsQuery.Dinnerclub>())
        }).map {
            pair ->
            pair.second.map {
                val id = it.id()
                val isParticipating = it.participants()!!.fold(false){
                    part,p -> part || (if(p.user()!!.id() == pair.first) !(p.cancelled()!!) else false)
                }
                if(pair.first == it.cook().id()){
                    // Current user is cook
                    //throw RuntimeException("Do we get the cook case")
                    CookDinnerclubItem(
                            id,
                            it.cancelled(),
                            it.shopping_complete(),
                            it.at(),
                            it.meal(),
                            isParticipating,
                            // Summing the participants
                            it.participants()!!.fold(0){
                                sum,p -> sum + if(!p.cancelled()!!) 1+p.guest_count()!! else 0
                            }
                    )
                }else{
                    // Current user is NOT cook
                    RegularDinnerclubItem(
                            id,
                            it.cancelled(),
                            it.shopping_complete(),
                            it.at(),
                            it.meal(),
                            isParticipating,
                            it.cook().display_name()!!)
                }
            }
        }.apply {
            subscribeToDinnerclubs(this)
        }
    }

    fun subscribeToDinnerclubs(observable: Observable<List<UpcommingDinnerclubItem>>){
        dinnerclubsObservable = observable
        view?.let {
            // TODO start loading indicator, and stop it when subscribe is called
            observable.subscribe ({
                res ->
                showDinnerclubs(res)
                Log.d("Madklub","successfully returned from logged in query")
            },{
                error -> when (error) {
                    // TODO do errors based on their type
                    // TODO inform user there is no connection
                    is ApolloNetworkException -> Log.d("Madklub","Network error, server probably down...")
                    else -> Log.d("Madklub Error!","We had the error: ${error.localizedMessage}")
                }
            }).addTo(uiDependentDisposables)
        }
    }

    fun showDinnerclubs(dinnerclubs: List<UpcommingDinnerclubItem>){
        this.dinnerclubs = dinnerclubs
        view?.showDinnerclubs(dinnerclubs)
    }

    interface MainView : BaseView {
        fun showDinnerclubs(dinnerclubs: List<UpcommingDinnerclubItem>)
        fun showMoreDinnerclubs(dinnerclubs: List<UpcommingDinnerclubItem>)
    }
}