package com.vest10.peter.madklubandroid.home_fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vest10.peter.madklubandroid.R
import com.vest10.peter.madklubandroid.SomeClass
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by peter on 02-09-17.
 */
class HomeFragment : Fragment() {
    @Inject lateinit var someClass : SomeClass
    @Inject @field:Named("something") lateinit var someString : String
    private var isChecked = false

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Injected someclass f",someClass.toString())
        Log.d("Injected somestring f",someString)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.home_fragment, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kf_selectable_icon.setOnClickListener {
            isChecked = !isChecked
            val stateSet = intArrayOf(android.R.attr.state_checked * if (isChecked) 1 else -1)
            kf_selectable_icon.setImageState(stateSet,true)
        }
    }
}