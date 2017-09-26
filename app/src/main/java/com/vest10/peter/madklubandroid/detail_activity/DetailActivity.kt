package com.vest10.peter.madklubandroid.detail_activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.support.v7.widget.Toolbar
import android.util.Log

import com.vest10.peter.madklubandroid.R
import com.vest10.peter.madklubandroid.application.BaseActivity
import com.vest10.peter.madklubandroid.networking.NetworkService
import kotlinx.android.synthetic.main.activity_detail.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class DetailActivity : BaseActivity() {
    companion object {
        val EXTRA_MEAL = "MadklubDetailMeal"
        val EXTRA_ID = "DetailDinnerclubID"
        val EXTRA_CANCELLED = "DetailMadklubCancelled"
        val EXTRA_HAS_SHOPPED = "DetailMadklubShopped"
        val EXTRA_IS_PARTICIPATING = "DetailMadklubParticipating"
        val MEAL_TRANSITION_KEY = "sharedMealTransitionKey"
        val BACKGROUND_TRANSITION_KEY = "sharedBackgroundTransitionKey"
        val ICON_PARTICIPATING_TRANSITION_KEY = "afjghlakerhgj"
        val ICON_SHOPPED_TRANSITION_KEY = "fjhgfhgfhghg"
    }

    @Inject
    lateinit var networkService: NetworkService

    override fun launchAuthenticatedNetworkRequests() {
        // TODO implement individual dinnerclub query by ID
        val getDinnerclub = networkService.query {
            DinnerclubDetailQuery.builder()
                    .id(intent.getStringExtra(EXTRA_ID))
                    .build()
        }
                .map {
                    Log.d("Madklub","Hurray!")
                }
                .subscribe {

                }
        disposables.add(getDinnerclub)
    }

    // TODO make these depend on the underlying model, aka object from apollo
    var cancelled = true
    var participating = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = DateTime.now().toString(DateTimeFormat.mediumDate())

        // Intent values
        dinnerclub_detail_meal.text = intent.getStringExtra(EXTRA_MEAL)
        setCancelledIcon(!intent.getBooleanExtra(EXTRA_CANCELLED,false))
        dinnerclub_detail_has_shopped_icon.setIconEnabled(intent.getBooleanExtra(EXTRA_HAS_SHOPPED,false),false)
        setParticipatingIcon(intent.getBooleanExtra(EXTRA_IS_PARTICIPATING,false))

        val fab = findViewById(R.id.fab) as FloatingActionButton
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
            setCancelledIcon(!cancelled)
        }
        dinnerclub_detail_is_participating_icon.setOnClickListener {
            setParticipatingIcon(!participating)
        }

        dinnerclub_detail_has_shopped_icon.setOnClickListener {
            dinnerclub_detail_has_shopped_icon.switchState()
        }
    }

    fun setCancelledIcon(value: Boolean) {
        cancelled = value
        val stateSet = intArrayOf(android.R.attr.state_checked * if (value) 1 else -1)
        dinnerclub_detail_is_cancelled_icon.setImageState(stateSet, false)
    }

    fun setParticipatingIcon(value: Boolean) {
        participating = value
        val stateSet = intArrayOf(android.R.attr.state_checked * if (value) 1 else -1)
        dinnerclub_detail_is_participating_icon.setImageState(stateSet, false)
    }
}
