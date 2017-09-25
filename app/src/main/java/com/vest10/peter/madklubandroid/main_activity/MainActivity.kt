package com.vest10.peter.madklubandroid.main_activity

import UpcommingDinnerclubsQuery
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.apollographql.apollo.exception.ApolloNetworkException
import com.vest10.peter.madklubandroid.R
import com.vest10.peter.madklubandroid.application.BaseActivity
import com.vest10.peter.madklubandroid.detail_activity.DetailActivity
import com.vest10.peter.madklubandroid.networking.NetworkService
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.CookDinnerclubItem
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.RegularDinnerclubItem
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.UpcommingDinnerclubsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
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
            adapter = UpcommingDinnerclubsAdapter({
                startActivity(Intent(this@MainActivity,DetailActivity::class.java))
            })
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = null
        }
        kitchen_list.setHasFixedSize(true)
    }

    override fun launchAuthenticatedNetworkRequests() {
        val getDinnerclubs = networkService.query {
            UpcommingDinnerclubsQuery.builder()
                    .startDate("2017-09-22T12:00:00.000Z")
                    .endDate("2017-12-22T12:00:00.000Z")
                    .build()
        }
        .delay(4,TimeUnit.SECONDS)
        .map {
            val me = it.data()?.me()
            Pair<String,List<UpcommingDinnerclubsQuery.Dinnerclub>>(me?.id()!!,me.kitchen()?.dinnerclubs()!!)
            //it.data()?.me()?.kitchen()?.dinnerclubs()
        }.onErrorReturn({
            Log.d("We had the error", it.localizedMessage)
            Log.d("We had the error", "$it")
            Pair("",emptyList<UpcommingDinnerclubsQuery.Dinnerclub>())
        }).map {
            pair ->
            pair.second.map {
                val id = it.id()
                Log.d("Madklub","User id is: $id")
                if(pair.first == it.cook().id()){
                    Log.d("Madklub","Do we get the cook case?!?!?!")
                    // Current user is cook
                    //throw RuntimeException("Do we get the cook case")
                    CookDinnerclubItem(
                            id,
                            it.cancelled(),
                            it.shopping_complete(),
                            it.at(),
                            it.meal(),
                            20)
                }else{
                    Log.d("Madklub","Do we get the regular case?!?!?!")
                    // Current user is NOT cook
                    RegularDinnerclubItem(
                            id,
                            it.cancelled(),
                            it.shopping_complete(),
                            it.at(),
                            it.meal(),
                            it.cook().display_name()!!)
                }
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
        disposables.add(getDinnerclubs)
    }
}
