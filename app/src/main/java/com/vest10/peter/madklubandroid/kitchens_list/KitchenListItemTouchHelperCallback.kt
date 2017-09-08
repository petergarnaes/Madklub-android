package com.vest10.peter.madklubandroid.kitchens_list

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.support.v4.view.ViewCompat.setTranslationX
import android.support.v4.view.ViewCompat.setAlpha
import android.opengl.ETC1.getWidth



/**
 * Created by peter on 07-09-17.
 */
class KitchenListItemTouchHelperCallback(private val adapter: KitchensListAdapter) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        //val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START
        return ItemTouchHelper.Callback.makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        if(viewHolder != null)
            adapter.notifyItemChanged(viewHolder.adapterPosition)
    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        //super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        viewHolder?.itemView?.translationX = dX
    }

    override fun isLongPressDragEnabled(): Boolean = false

    //override fun isItemViewSwipeEnabled(): Boolean = true
}