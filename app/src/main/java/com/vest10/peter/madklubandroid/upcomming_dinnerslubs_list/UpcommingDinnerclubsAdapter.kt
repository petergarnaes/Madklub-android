package com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.vest10.peter.madklubandroid.commons.adapter.AdapterConstants
import com.vest10.peter.madklubandroid.commons.adapter.ViewType
import com.vest10.peter.madklubandroid.commons.adapter.ViewTypeDelegateAdapter

/**
 * Created by peter on 12-09-17.
 */
class UpcommingDinnerclubsAdapter(private val onClickListener: ((UpcommingDinnerclubItem,RecyclerView.ViewHolder) -> Unit)?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        RegularDinnerclubsDelegateAdapter.DinnerClubCancelledListener,
        CookDinnerclubsDelegateAdapter.DinnerClubHasShoppedListener {

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val loadingItem = object : ViewType {
        override fun getViewType(): Int = AdapterConstants.LOADING
    }

    init {
        delegateAdapters.put(AdapterConstants.LOADING,LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConstants.REGULAR_DINNERCLUB,RegularDinnerclubsDelegateAdapter(this,onClickListener))
        delegateAdapters.put(AdapterConstants.COOKING_DINNERCLUB,CookDinnerclubsDelegateAdapter(this,onClickListener))
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder,items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            delegateAdapters.get(viewType).onCreateViewHolder(parent)

    fun addDinnerclubs(dinnerclubs: List<UpcommingDinnerclubItem>) {
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)
        items.addAll(dinnerclubs)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition, items.size + 1 /* plus loading item*/)
    }
    override fun onDinnerclubCancelled(position: Int,isCancelled: Boolean) {
        notifyItemChanged(position)
        // TODO optimistic server update
    }

    override fun onDinnerclubShopped(position: Int, hasShopped: Boolean) {
        notifyItemChanged(position)
        // TODO optimistic server update
    }
}