package com.vest10.peter.madklubandroid.android

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.vest10.peter.madklubandroid.application.MadklubApplication

/**
 * Created by peter on 02-09-17.
 */
class MadklubPreferences(application: MadklubApplication) {
    companion object {
        val ACCOUNT_USERNAME_KEY = "MadklubUsername"
    }
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    fun chooseAccount(username: String) {
        val settings = sharedPreferences.edit()
        settings.putString(ACCOUNT_USERNAME_KEY,username)
        settings.apply()
    }

    fun getAccountUsername() = sharedPreferences.getString(ACCOUNT_USERNAME_KEY,"")
}