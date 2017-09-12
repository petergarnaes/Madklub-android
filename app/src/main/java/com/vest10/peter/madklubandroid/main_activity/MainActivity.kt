package com.vest10.peter.madklubandroid.main_activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import com.vest10.peter.madklubandroid.R
import com.vest10.peter.madklubandroid.SomeClass
import com.vest10.peter.madklubandroid.kitchens_list.KitchenListItemTouchHelperCallback
import com.vest10.peter.madklubandroid.kitchens_list.KitchensListAdapter
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.UpcommingDinnerclubItem
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.UpcommingDinnerclubsAdapter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    // For some reason this will make dagger complain :/
    //val something: List<KitchensQuery.Kitchen> = emptyList()
    private var isChecked = false
    @Inject
    lateinit var someClass : SomeClass
    @Inject
    lateinit var client : ApolloClient
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
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
        kitchen_list.apply {
            setHasFixedSize(true)
            adapter = UpcommingDinnerclubsAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = null
        }
        kitchen_list.setHasFixedSize(true)

        Rx2Apollo.from(client.query(UpcommingDinnerclubsQuery.builder().build()))
                .map {
                    it.data()?.me()?.kitchen()?.dinnerclubs()
                }
                .onErrorReturn({
                    Log.d("We had the error", it.localizedMessage)
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

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
}
