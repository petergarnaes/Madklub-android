package com.vest10.peter.madklubandroid.detail_activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import com.vest10.peter.madklubandroid.R
import kotlinx.android.synthetic.main.activity_detail.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class DetailActivity : AppCompatActivity() {
    companion object {
        val EXTRA_MEAL = "MadklubDetailMeal"
    }

    var checked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = DateTime.now().toString(DateTimeFormat.mediumDate())

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        val stateSet = intArrayOf(android.R.attr.state_checked * if (checked) 1 else -1)
        dinnerclub_detail_is_cancelled_icon.setImageState(stateSet, false)
        dinnerclub_detail_is_cancelled_icon.setOnClickListener {
            checked = !checked
            val stateSet2 = intArrayOf(android.R.attr.state_checked * if (checked) 1 else -1)
            dinnerclub_detail_is_cancelled_icon.setImageState(stateSet2, false)
        }

    }
}
