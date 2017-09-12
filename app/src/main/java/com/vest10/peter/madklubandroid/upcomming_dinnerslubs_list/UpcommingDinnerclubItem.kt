package com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list

import com.vest10.peter.madklubandroid.commons.adapter.AdapterConstants
import com.vest10.peter.madklubandroid.commons.adapter.ViewType
import java.util.*

/**
 * Created by peter on 12-09-17.
 */
data class UpcommingDinnerclubItem(var id: String,
                                   var cancelled: Boolean,
                                   var shopping_complete: Boolean,
                                   var at: Date) : ViewType {
    override fun getViewType(): Int = AdapterConstants.REGULAR_DINNERCLUB
}