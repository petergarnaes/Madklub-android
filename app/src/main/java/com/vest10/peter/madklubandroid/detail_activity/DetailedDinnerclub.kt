package com.vest10.peter.madklubandroid.detail_activity

import org.joda.time.DateTime

/**
 * Created by peter on 27-09-17.
 */
data class DetailedDinnerclub(
        var id: String,
        var at: DateTime,
        var cancelled: Boolean,
        var shoppingComplete: Boolean,
        var totalCost: Double,
        var meal: String,
        var cook: DetailedCook,
        var participants: List<DetailedParticipants>)

data class DetailedCook(var id: String,var name: String)

data class DetailedParticipants(var id: String,var guestCount: Int,var willBeLate: Boolean)