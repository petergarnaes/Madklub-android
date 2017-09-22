package com.vest10.peter.madklubandroid.main_activity

import UpcommingDinnerclubsQuery
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.exception.ApolloNetworkException
import com.apollographql.apollo.rx2.Rx2Apollo
import com.vest10.peter.madklubandroid.R
import com.vest10.peter.madklubandroid.SomeClass
import com.vest10.peter.madklubandroid.application.BaseActivity
import com.vest10.peter.madklubandroid.authentication.MadklubUserManager
import com.vest10.peter.madklubandroid.depenedency_injection.components.UserComponent
import com.vest10.peter.madklubandroid.main_activity.di.MainActivityDependenciesModule
import com.vest10.peter.madklubandroid.networking.NetworkService
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.UpcommingDinnerclubItem
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.UpcommingDinnerclubsAdapter
import com.vest10.peter.madklubandroid.user.BaseUserActivity
import com.vest10.peter.madklubandroid.user.User
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class MainActivity : BaseActivity() {
    //@Inject
    //lateinit var user : User
    //@Inject
    //lateinit var someClass : SomeClass
    @Inject
    lateinit var networkService: NetworkService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        kitchen_list.apply {
            setHasFixedSize(true)
            adapter = UpcommingDinnerclubsAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = null
        }
        kitchen_list.setHasFixedSize(true)

        val getDinnerclubs = networkService.query {
            UpcommingDinnerclubsQuery.builder()
                    .startDate("2017-01-22T12:00:00.000Z")
                    .endDate("2017-10-22T12:00:00.000Z")
                    .build()
        }.map {
            it.data()?.me()?.kitchen()?.dinnerclubs()
        }.onErrorReturn({
            Log.d("We had the error", it.localizedMessage)
            Log.d("We had the error", "$it")
            emptyList<UpcommingDinnerclubsQuery.Dinnerclub>()
        }).map {
            it.map {
                UpcommingDinnerclubItem(
                        it.id(),
                        it.cancelled()!!,
                        it.shopping_complete()!!,
                        it.at()!!)
            }
        }.subscribe ({
            res ->
            (kitchen_list.adapter as UpcommingDinnerclubsAdapter).addDinnerclubs(res)
            Log.d("Madklub","successfully returned from logged in query")
        },{
            // TODO do errors based on their type
            error -> when (error) {
                // TODO inform user there is no connection
                is ApolloNetworkException -> Log.d("Madklub","Network error, server probably down...")
            }
            Log.d("Madklub","We had an error")
        })
        subscriptions.add(getDinnerclubs)
    }
}
