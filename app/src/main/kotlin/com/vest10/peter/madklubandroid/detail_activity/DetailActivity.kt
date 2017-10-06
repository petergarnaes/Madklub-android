package com.vest10.peter.madklubandroid.detail_activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.support.v7.widget.Toolbar
import android.util.Log

import com.vest10.peter.madklubandroid.R
import com.vest10.peter.madklubandroid.application.BaseActivity
import com.vest10.peter.madklubandroid.depenedency_injection.components.ConfigPersistentComponent
import com.vest10.peter.madklubandroid.networking.NetworkService
import kotlinx.android.synthetic.main.activity_detail.*
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class DetailActivity : BaseActivity<DetailPresenter.DetailView,DetailPresenter>(), DetailPresenter.DetailView {

    companion object {
        val EXTRA_MEAL = "MadklubDetailMeal"
        val EXTRA_ID = "DetailDinnerclubID"
        val EXTRA_CANCELLED = "DetailMadklubCancelled"
        val EXTRA_HAS_SHOPPED = "DetailMadklubShopped"
        val EXTRA_IS_PARTICIPATING = "DetailMadklubParticipating"
        val EXTRA_COOK_NAME = "DetailMadklubCookName"
        val MEAL_TRANSITION_KEY = "sharedMealTransitionKey"
        val BACKGROUND_TRANSITION_KEY = "sharedBackgroundTransitionKey"
        val ICON_PARTICIPATING_TRANSITION_KEY = "iconParticipatingTransitionKey"
        val ICON_SHOPPED_TRANSITION_KEY = "iconHasShoppedTransitionKey"
    }

    override fun launchAuthenticatedNetworkRequests() {
        presenter.getDinnerclub(intent.getStringExtra(EXTRA_ID))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = ""

        // Intent values
        // TODO only used for transition, so ignore this when presenter thinks so?
        dinnerclub_detail_meal.text = if(intent.hasExtra(EXTRA_MEAL))
           intent.getStringExtra(EXTRA_MEAL)
        else
            "..."
        setCancelledIcon(intent.getBooleanExtra(EXTRA_CANCELLED,false))
        dinnerclub_detail_has_shopped_icon.setIconEnabled(intent.getBooleanExtra(EXTRA_HAS_SHOPPED,false),false)
        setParticipatingIcon(intent.getBooleanExtra(EXTRA_IS_PARTICIPATING,false))
        //dinnerclub_detail_cook.text = if(intent.hasExtra(EXTRA_COOK_NAME))
        //    intent.getStringExtra(EXTRA_COOK_NAME)
        //else
        //    "..."

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // Only animate shared elements if requested
        if (intent.hasExtra(MEAL_TRANSITION_KEY))
            ViewCompat.setTransitionName(dinnerclub_detail_meal,intent.getStringExtra(MEAL_TRANSITION_KEY))
        if(intent.hasExtra(BACKGROUND_TRANSITION_KEY))
            ViewCompat.setTransitionName(view_background,intent.getStringExtra(BACKGROUND_TRANSITION_KEY))
        if (intent.hasExtra(ICON_PARTICIPATING_TRANSITION_KEY))
            ViewCompat.setTransitionName(dinnerclub_detail_is_participating_icon,intent.getStringExtra(ICON_PARTICIPATING_TRANSITION_KEY))
        if (intent.hasExtra(ICON_SHOPPED_TRANSITION_KEY))
            ViewCompat.setTransitionName(dinnerclub_detail_has_shopped_icon,intent.getStringExtra(ICON_SHOPPED_TRANSITION_KEY))

        dinnerclub_detail_is_cancelled_icon.setOnClickListener {
            presenter.cancelIconClicked()
        }
        dinnerclub_detail_is_participating_icon.setOnClickListener {
            presenter.participationIconClicked()
        }
        dinnerclub_detail_has_shopped_icon.setOnClickListener {
            presenter.shoppingIconClicked()
        }
    }

    override fun showDinnerclub(dinnerclub: DetailedDinnerclub) {
        dinnerclub_detail_cook.text = dinnerclub.cook.name
        dinnerclub_detail_meal.text = dinnerclub.meal
        setCancelledIcon(dinnerclub.cancelled)
        dinnerclub_detail_has_shopped_icon.setIconEnabled(dinnerclub.shoppingComplete,false)
        supportActionBar?.title = dinnerclub.at.toString(DateTimeFormat.mediumDate())
    }

    override fun injectMembers(configPersistentComponent: ConfigPersistentComponent) {
        configPersistentComponent.detailActivityComponent().inject(this)
    }

    override fun setCancelledIcon(value: Boolean) {
        val stateSet = intArrayOf(android.R.attr.state_checked * if (!value) 1 else -1)
        dinnerclub_detail_is_cancelled_icon.setImageState(stateSet, false)
    }

    override fun setParticipatingIcon(value: Boolean) {
        val stateSet = intArrayOf(android.R.attr.state_checked * if (value) 1 else -1)
        dinnerclub_detail_is_participating_icon.setImageState(stateSet, false)
    }

    override fun setShoppingIcon(value: Boolean){
        dinnerclub_detail_has_shopped_icon.setIconEnabled(value,true)
    }
}
