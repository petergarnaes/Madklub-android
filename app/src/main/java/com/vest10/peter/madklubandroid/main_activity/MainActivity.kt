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
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.UpcommingDinnerclubItem
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.UpcommingDinnerclubsAdapter
import com.vest10.peter.madklubandroid.user.BaseUserActivity
import com.vest10.peter.madklubandroid.user.User
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    // For some reason this will make dagger complain :/
    //val something: List<KitchensQuery.Kitchen> = emptyList()
    private var isChecked = false
    //@Inject
    //lateinit var user : User
    //@Inject
    //lateinit var someClass : SomeClass
    @Inject
    lateinit var client : ApolloClient
    @Inject
    lateinit var userManager: MadklubUserManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecylcerView setup
        /*kitchen_list.apply {
            setHasFixedSize(true)
            val kitchensAdapter = KitchensListAdapter(listOf(),this@MainActivity)
            adapter = kitchensAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            val touchHelper = ItemTouchHelper(KitchenListItemTouchHelperCallback(kitchensAdapter))
            touchHelper.attachToRecyclerView(kitchen_list)
        }*/
        //Log.d("Madklub","We have the SomeClass: $someClass")
        //Log.d("Madklub","We have the user: ${user.username}")
        kitchen_list.apply {
            setHasFixedSize(true)
            adapter = UpcommingDinnerclubsAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = null
        }
        kitchen_list.setHasFixedSize(true)

        Rx2Apollo.from(client.query(UpcommingDinnerclubsQuery.builder()
                .startDate("2017-01-22T12:00:00.000Z")
                .endDate("2017-10-22T12:00:00.000Z")
                .build()))
                .doOnError {
                    when (it) {
                        is ApolloHttpException -> when (it.code()) {
                            401,403 -> userManager.invalidateAuthToken() // accountManager.invalidateToken(accountType,oldToken)
                            else -> 1
                        }
                    }
                }
                .map {
                    it.data()?.me()?.kitchen()?.dinnerclubs()
                }
                .onErrorReturn({
                    Log.d("We had the error", it.localizedMessage)
                    Log.d("We had the error", "$it")
                    emptyList<UpcommingDinnerclubsQuery.Dinnerclub>()
                })
                .map {
                    it.map {
                        UpcommingDinnerclubItem(
                                it.id(),
                                it.cancelled()!!,
                                it.shopping_complete()!!,
                                it.at()!!)
                    }
                }
                /*.retryWhen {
                    error -> error.flatMap {
                        Observable.just(null)
                    }
                }*/
                .subscribe {
                    res ->
                    (kitchen_list.adapter as UpcommingDinnerclubsAdapter).addDinnerclubs(res)
                    Log.d("Madklub","successfully returned from logged in query")
                }
        /*Rx2Apollo.from(client.query(KitchensQuery.builder().build()))
                .map { it.data()?.kitchens() }
                .onErrorReturn({
                    Log.d("We had the error", it.localizedMessage)
                    emptyList<KitchensQuery.Kitchen>()
                })
                .subscribe { kitchens ->
                    //val data = res.data()
                    //val data = kitchens
                    var log = "data is null..."
                    //if (data != null) {
                        log = "Have kitchens - "
                        var kitchensString = "null"
                        //val kitchens = data.kitchens()
                        if (kitchens != null) {
                            kitchensString = "["
                            for (k in kitchens)
                                kitchensString += k.name() + ","
                            kitchensString += "]"
                            // Update
                            (kitchen_list.adapter as KitchensListAdapter).kitchensList = kitchens
                            kitchen_list.adapter.notifyDataSetChanged()
                        }
                        log += kitchensString
                    //}
                    Log.d("Kitchens response", log)
                }*/
    }
    //override fun setupUserComponent(userComponent: UserComponent) {
    //    userComponent.plus(MainActivityDependenciesModule()).inject(this)
    //}
}
