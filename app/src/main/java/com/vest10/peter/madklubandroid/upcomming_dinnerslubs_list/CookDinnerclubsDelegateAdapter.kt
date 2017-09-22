package com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.vest10.peter.madklubandroid.R
import com.vest10.peter.madklubandroid.commons.adapter.ViewType
import com.vest10.peter.madklubandroid.commons.adapter.ViewTypeDelegateAdapter
import com.vest10.peter.madklubandroid.commons.inflate
import kotlinx.android.synthetic.main.upcomming_dinnerclub_cook_item.view.*

/**
 * Created by peter on 9/22/17.
 */
class CookDinnerclubsDelegateAdapter(val shoppedListener: DinnerClubHasShoppedListener) : ViewTypeDelegateAdapter {
    interface DinnerClubHasShoppedListener {
        fun onDinnerclubShopped(position: Int,hasShopped: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = TurnsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder
        val upcommingDinnerclub = item as CookDinnerclubItem
        with(holder.itemView) {
            dinnerclub_item_cook_has_shopped_icon.setOnClickListener {
                //dinnerclub_item_cook_has_shopped_icon.switchState(true)
                upcommingDinnerclub.shopping_complete = !upcommingDinnerclub.shopping_complete
                dinnerclub_item_cook_has_shopped_icon.setIconEnabled(!dinnerclub_item_cook_has_shopped_icon.isIconEnabled,true)
                val has_shopped = dinnerclub_item_cook_has_shopped_icon.isIconEnabled
                Log.d("Madklub","Has shopped: $has_shopped")
                shoppedListener.onDinnerclubShopped(holder.adapterPosition,has_shopped)
            }
        }
        holder.bind(upcommingDinnerclub)
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.upcomming_dinnerclub_cook_item)) {
        fun bind(item: CookDinnerclubItem) = with(itemView) {
            dinnerclub_item_cook_date.text = item.at.dayOfMonth().asText
            dinnerclub_item_cook_meal.text = item.meal
            dinnerclub_item_cook_participant_count.text = item.nrParticipants.toString()
            dinnerclub_item_cook_has_shopped_icon.setIconEnabled(item.shopping_complete,false)
        }
    }
}