package com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.vest10.peter.madklubandroid.R
import com.vest10.peter.madklubandroid.commons.adapter.ViewType
import com.vest10.peter.madklubandroid.commons.adapter.ViewTypeDelegateAdapter
import com.vest10.peter.madklubandroid.commons.inflate
import kotlinx.android.synthetic.main.item_loading.view.*

/**
 * Created by peter on 12-09-17.
 */

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {
    var loadMore: TextView? = null
    var progress: ProgressBar? = null

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder{
        val v = TurnsViewHolder(parent)
        loadMore = v.itemView.load_more
        progress = v.itemView.progress
        return v
    }

    fun toggleLoadMore(isVisible: Boolean){
        if (isVisible){
            loadMore?.visibility = View.VISIBLE
            progress?.visibility = View.GONE
        } else {
            loadMore?.visibility = View.GONE
            progress?.visibility = View.VISIBLE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class TurnsViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.item_loading))
}