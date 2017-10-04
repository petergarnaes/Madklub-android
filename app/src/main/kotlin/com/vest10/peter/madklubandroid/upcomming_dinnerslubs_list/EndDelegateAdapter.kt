package com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.vest10.peter.madklubandroid.R
import com.vest10.peter.madklubandroid.commons.adapter.ViewType
import com.vest10.peter.madklubandroid.commons.adapter.ViewTypeDelegateAdapter
import com.vest10.peter.madklubandroid.commons.inflate

/**
 * Created by peter on 9/27/17.
 */
class EndDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = TurnsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class TurnsViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.item_end))
}