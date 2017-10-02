package com.vest10.peter.madklubandroid.main_activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.vest10.peter.madklubandroid.R
import com.vest10.peter.madklubandroid.application.BaseActivity
import com.vest10.peter.madklubandroid.detail_activity.DetailActivity
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.CookDinnerclubItem
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.RegularDinnerclubItem
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.UpcommingDinnerclubsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.upcomming_dinnerclub_cook_item.view.*
import kotlinx.android.synthetic.main.upcomming_dinnerclub_item.view.*
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import com.vest10.peter.madklubandroid.depenedency_injection.components.ConfigPersistentComponent
import com.vest10.peter.madklubandroid.commons.InfiniteScrollListener
import com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list.UpcommingDinnerclubItem
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class MainActivity : BaseActivity<MainPresenter.MainView,MainPresenter>(), MainPresenter.MainView {
    override fun injectMembers(configPersistentComponent: ConfigPersistentComponent) {
        configPersistentComponent.mainActivityComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        kitchen_list.apply {
            setHasFixedSize(true)
            val upcommingAdapter = UpcommingDinnerclubsAdapter(this@MainActivity::performSharedTransactionToDetailActivity)
            adapter = upcommingAdapter
            val manager = LinearLayoutManager(this@MainActivity)
            layoutManager = manager
            itemAnimator = null
            val mDividerItemDecoration = DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
            )
            addItemDecoration(mDividerItemDecoration)
            addOnScrollListener(InfiniteScrollListener(this@MainActivity::loadMoreDinnerclubs,upcommingAdapter,manager))
        }
    }

    fun loadMoreDinnerclubs(){
        // TODO implement segment loading
        Log.d("Madklub","Loading more dinnerclubs")
        // Should trigger end reached
        Observable.just(Unit).delay(3,TimeUnit.SECONDS).subscribe {
            showDinnerclubs(emptyList())
        }
    }

    override fun showDinnerclubs(dinnerclubs: List<UpcommingDinnerclubItem>) {
        (kitchen_list.adapter as UpcommingDinnerclubsAdapter).concatDinnerclubs(dinnerclubs)
    }

    override fun showMoreDinnerclubs(dinnerclubs: List<UpcommingDinnerclubItem>){

    }

    override fun launchAuthenticatedNetworkRequests() {
        presenter.getDinnerclubs()
    }

    fun performSharedTransactionToDetailActivity(dinnerclubItem: UpcommingDinnerclubItem,holder: RecyclerView.ViewHolder){
        val intent = Intent(this@MainActivity,DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID,dinnerclubItem.id)
        intent.putExtra(DetailActivity.EXTRA_MEAL,dinnerclubItem.meal)
        intent.putExtra(DetailActivity.EXTRA_HAS_SHOPPED,dinnerclubItem.shopping_complete)
        intent.putExtra(DetailActivity.EXTRA_CANCELLED,dinnerclubItem.cancelled)
        intent.putExtra(DetailActivity.EXTRA_IS_PARTICIPATING,dinnerclubItem.isParticipating)

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
    }
}
