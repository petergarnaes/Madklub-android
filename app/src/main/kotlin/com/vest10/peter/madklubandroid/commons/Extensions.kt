@file:JvmName("ExtensionsUtils")
package com.vest10.peter.madklubandroid.commons

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.rx2.Rx2Apollo
import com.squareup.picasso.Picasso
import com.vest10.peter.madklubandroid.R
import io.reactivex.Observable

/**
 * Created by peter on 31-08-17.
 */

fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId,this,false)
}

fun ImageView.loadImg(imageUrl: String) {
    if (TextUtils.isEmpty(imageUrl))
        Picasso.with(context).load(R.mipmap.ic_launcher).into(this)
    else
        Picasso.with(context).load(imageUrl).into(this)
}