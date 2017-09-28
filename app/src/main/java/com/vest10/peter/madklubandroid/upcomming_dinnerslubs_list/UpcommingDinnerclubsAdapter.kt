package com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
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
    private var recyclerView: RecyclerView? = null
    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val loadingItem = object : ViewType {
        override fun getViewType(): Int = AdapterConstants.LOADING
    }
    private val endItem = object : ViewType {
        override fun getViewType(): Int = AdapterConstants.END
    }

    init {
        delegateAdapters.put(AdapterConstants.LOADING,LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConstants.REGULAR_DINNERCLUB,RegularDinnerclubsDelegateAdapter(this,onClickListener))
        delegateAdapters.put(AdapterConstants.COOKING_DINNERCLUB,CookDinnerclubsDelegateAdapter(this,onClickListener))
        delegateAdapters.put(AdapterConstants.END,EndDelegateAdapter())
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder,items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            delegateAdapters.get(viewType).onCreateViewHolder(parent)

    fun addDinnerclubs(dinnerclubs: List<UpcommingDinnerclubItem>) {
        var visibleItemCount = recyclerView?.childCount ?: 0
        Log.d("Madklub","Before nofified, recycler child count: $visibleItemCount and total count: $itemCount")
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)
        items.addAll(dinnerclubs)
        if(dinnerclubs.isEmpty()){
            items.add(endItem)
        } else {
            items.add(loadingItem)
        }
        notifyItemRangeChanged(initPosition, items.size + 1 /* plus loading item*/)
    }

    fun shortList(){
        val initPosition = items.size - 1
        if(items[initPosition] != endItem){
            items.removeAt(initPosition)
            // TODO make custom "Load more" button?
            items.add(endItem)
            notifyItemChanged(initPosition)
        }
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