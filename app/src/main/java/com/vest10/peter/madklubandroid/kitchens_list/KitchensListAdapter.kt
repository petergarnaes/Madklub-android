package com.vest10.peter.madklubandroid.kitchens_list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vest10.peter.madklubandroid.R

/**
 * Created by peter on 07-09-17.
 */
class KitchensListAdapter(var kitchensList: List<KitchensQuery.Kitchen>, val ctx : Context) : RecyclerView.Adapter<KitchensViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): KitchensViewHolder {
        val layoutInflater = LayoutInflater.from(ctx)
        val kitchenItemView = layoutInflater.inflate(R.layout.kitchen_item_view,parent,false)
        return KitchensViewHolder(kitchenItemView)
    }

    override fun getItemCount(): Int = kitchensList.count()

    override fun onBindViewHolder(holder: KitchensViewHolder, position: Int) {
        val kitchen = kitchensList[position]
        holder.update(kitchen)
    }
}