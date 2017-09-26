package com.vest10.peter.madklubandroid.main_activity

import UpcommingDinnerclubsQuery
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.apollographql.apollo.exception.ApolloNetworkException
import com.vest10.peter.madklubandroid.R
import com.vest10.peter.madklubandroid.application.BaseActivity
import com.vest10.peter.madklubandroid.detail_activity.DetailActivity
import com.vest10.peter.madklubandroid.networking.NetworkService
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.CookDinnerclubItem
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.RegularDinnerclubItem
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.UpcommingDinnerclubsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.upcomming_dinnerclub_cook_item.view.*
import kotlinx.android.synthetic.main.upcomming_dinnerclub_item.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import android.support.v7.widget.DividerItemDecoration



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
                dinnerclubItem,holder ->
                val intent = Intent(this@MainActivity,DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID,dinnerclubItem.id)
                intent.putExtra(DetailActivity.EXTRA_MEAL,dinnerclubItem.meal)

                // Shared tansition
                var transitionViewMeal: View? = null
                var transitionViewIcon: View? = null
                var transitionNameIcon: String? = null
                var transitionViewBackground: View? = null
                when(dinnerclubItem){
                    is RegularDinnerclubItem -> {
                        transitionViewMeal = holder.itemView.dinnerclub_item_meal
                        transitionViewBackground = holder.itemView.view_background
                        transitionViewIcon = holder.itemView.kf_cancel_icon
                        transitionNameIcon = ViewCompat.getTransitionName(transitionViewIcon)
                        intent.putExtra(DetailActivity.ICON_PARTICIPATING_TRANSITION_KEY,transitionNameIcon)
                    }
                    is CookDinnerclubItem -> {
                        transitionViewMeal = holder.itemView.dinnerclub_item_cook_meal
                        transitionViewBackground = holder.itemView.view_background_cook
                        transitionViewIcon = holder.itemView.dinnerclub_item_cook_has_shopped_icon
                        transitionNameIcon = ViewCompat.getTransitionName(transitionViewIcon)
                        intent.putExtra(DetailActivity.ICON_SHOPPED_TRANSITION_KEY,transitionNameIcon)
                    }
                }

                val transitionNameMeal = ViewCompat.getTransitionName(transitionViewMeal)
                intent.putExtra(DetailActivity.MEAL_TRANSITION_KEY,transitionNameMeal)
                val transitionNameBackground = ViewCompat.getTransitionName(transitionViewBackground)
                intent.putExtra(DetailActivity.BACKGROUND_TRANSITION_KEY,transitionNameBackground)

                val t1 = android.support.v4.util.Pair<View,String>(transitionViewMeal,transitionNameMeal)
                val t2 = android.support.v4.util.Pair<View,String>(transitionViewBackground,transitionNameBackground)
                val t3 = android.support.v4.util.Pair<View,String>(transitionViewIcon,transitionNameIcon)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity,t1,t2,t3)

                startActivity(intent,options.toBundle())
            })
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = null
            val mDividerItemDecoration = DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
            )
            addItemDecoration(mDividerItemDecoration)
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
