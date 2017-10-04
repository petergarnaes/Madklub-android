package com.vest10.peter.madklubandroid.kitchens_list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.vest10.peter.madklubandroid.R

/**
 * Created by peter on 07-09-17.
 */
class KitchensViewHolder(itemView: View,var checked: Boolean) : RecyclerView.ViewHolder(itemView) {
    var nameField: TextView = itemView.findViewById(R.id.kitchen_name)

    fun update(kitchen: KitchensQuery.Kitchen) {
        nameField.text = kitchen.name()
    }
}