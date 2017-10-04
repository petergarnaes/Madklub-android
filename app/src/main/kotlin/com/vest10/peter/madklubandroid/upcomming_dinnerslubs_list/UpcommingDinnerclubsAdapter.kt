package com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.vest10.peter.madklubandroid.commons.adapter.AdapterConstants
import com.vest10.peter.madklubandroid.commons.adapter.ViewType
import com.vest10.peter.madklubandroid.commons.adapter.ViewTypeDelegateAdapter
import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ActivityScope
import com.vest10.peter.madklubandroid.main_activity.MainActivity
import com.vest10.peter.madklubandroid.main_activity.MainPresenter
import javax.inject.Inject

/**
 * Created by peter on 12-09-17.
 */
@ActivityScope
class UpcommingDinnerclubsAdapter @Inject constructor(
        private val mainActivity: MainActivity,
        private val mainPresenter: MainPresenter
        ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        RegularDinnerclubsDelegateAdapter.DinnerClubCancelledListener,
        CookDinnerclubsDelegateAdapter.DinnerClubHasShoppedListener {
    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val loadingItem = object : ViewType {
        override fun getViewType(): Int = AdapterConstants.LOADING
    }
    private val endItem = object : ViewType {
        override fun getViewType(): Int = AdapterConstants.END
    }
    private val loadingDelegateAdapter = LoadingDelegateAdapter()

    init {
        delegateAdapters.put(AdapterConstants.LOADING,loadingDelegateAdapter)
        delegateAdapters.put(AdapterConstants.REGULAR_DINNERCLUB,RegularDinnerclubsDelegateAdapter(this,mainActivity::performSharedTransactionToDetailActivity))
        delegateAdapters.put(AdapterConstants.COOKING_DINNERCLUB,CookDinnerclubsDelegateAdapter(this,mainActivity::performSharedTransactionToDetailActivity))
        delegateAdapters.put(AdapterConstants.END,EndDelegateAdapter())
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder,items[position])
    }
    //fun blah(d: UpcommingDinnerclubsQuery.Dinnerclub){

    //}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            delegateAdapters.get(viewType).onCreateViewHolder(parent)

    fun clearAndSetDinnerclubs(dinnerclubs: List<UpcommingDinnerclubItem>){
        items.clear()
        items.addAll(dinnerclubs)
        if(dinnerclubs.isEmpty()){
            // TODO special end plate when no dinnerclubs?
            items.add(endItem)
        } else {
            items.add(loadingItem)
        }
        notifyDataSetChanged()
    }

    fun toggleLoadMore(isVisible: Boolean){
        loadingDelegateAdapter.toggleLoadMore(isVisible)
    }

    fun concatDinnerclubs(dinnerclubs: List<UpcommingDinnerclubItem>) {
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

    override fun onCancellingParticipation(position: Int, isParticipating: Boolean) {
        mainPresenter.cancelParticipation(!isParticipating,(items[position] as UpcommingDinnerclubItem).id)
    }

    override fun onDinnerclubShopped(position: Int, hasShopped: Boolean) {
        notifyItemChanged(position)
        // TODO optimistic server update
    }
}