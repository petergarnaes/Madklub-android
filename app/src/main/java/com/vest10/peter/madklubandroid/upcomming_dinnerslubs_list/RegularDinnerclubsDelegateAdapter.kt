package com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.vest10.peter.madklubandroid.R
import com.vest10.peter.madklubandroid.commons.adapter.ViewType
import com.vest10.peter.madklubandroid.commons.adapter.ViewTypeDelegateAdapter
import com.vest10.peter.madklubandroid.commons.inflate
import kotlinx.android.synthetic.main.upcomming_dinnerclub_item.view.*

/**
 * Created by peter on 12-09-17.
 */
class RegularDinnerclubsDelegateAdapter(
        val cancelAction: DinnerClubCancelledListener,
        private val onItemSelected: ((UpcommingDinnerclubItem,RecyclerView.ViewHolder) -> Unit)?) : ViewTypeDelegateAdapter {
    interface DinnerClubCancelledListener {
        fun onDinnerclubCancelled(position: Int,isCancelled: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = TurnsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder
        val upcommingDinnerclub = item as RegularDinnerclubItem
        ViewCompat.setTransitionName(holder.itemView.view_background,"toDetailBackgroundTransition"+holder.adapterPosition)
        ViewCompat.setTransitionName(holder.itemView.dinnerclub_item_meal,"toDetailMealTransition"+holder.adapterPosition)
        ViewCompat.setTransitionName(holder.itemView.kf_cancel_icon,"toDetailIconTransition"+holder.adapterPosition)
        with(holder.itemView) {
            kf_cancel_icon.setOnClickListener {
                upcommingDinnerclub.cancelled = !upcommingDinnerclub.cancelled
                val stateSet = intArrayOf(android.R.attr.state_checked * if (upcommingDinnerclub.cancelled) 1 else -1)
                kf_cancel_icon.setImageState(stateSet, false)
                cancelAction.onDinnerclubCancelled(holder.adapterPosition,upcommingDinnerclub.cancelled)
            }
            if(onItemSelected != null)
                setOnClickListener {
                    onItemSelected.invoke(upcommingDinnerclub,holder)
                }
        }
        holder.bind(upcommingDinnerclub)
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.upcomming_dinnerclub_item)) {
        fun bind(item: RegularDinnerclubItem) = with(itemView) {
            dinnerclub_item_date.text = item.at.dayOfMonth().asText
            dinnerclub_item_meal.text = item.meal
            dinnerclub_item_cook.text = item.cookName
            val stateSet = intArrayOf(android.R.attr.state_checked * if (item.cancelled) 1 else -1)
            kf_cancel_icon.setImageState(stateSet, false)
        }
    }
}