package com.vest10.peter.madklubandroid.upcomming_dinnerslubs_list

import com.vest10.peter.madklubandroid.commons.adapter.AdapterConstants
import com.vest10.peter.madklubandroid.commons.adapter.ViewType
import org.joda.time.DateTime

/**
 * Created by peter on 12-09-17.
 */
abstract class UpcommingDinnerclubItem(var id: String,
                                        var cancelled: Boolean,
                                        var shopping_complete: Boolean,
                                        var at: DateTime,
                                        var meal: String,
                                        var isParticipating: Boolean) : ViewType