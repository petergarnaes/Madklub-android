package com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list

import com.vest10.peter.madklubandroid.commons.adapter.AdapterConstants
import org.joda.time.DateTime

/**
 * Created by peter on 9/22/17.
 */
class RegularDinnerclubItem(
        id: String,
        cancelled: Boolean,
        shopping_complete: Boolean,
        at: DateTime,
        meal: String,
        val cookName: String) : UpcommingDinnerclubItem(id,cancelled,shopping_complete,at,meal) {
    override fun getViewType(): Int = AdapterConstants.REGULAR_DINNERCLUB
}