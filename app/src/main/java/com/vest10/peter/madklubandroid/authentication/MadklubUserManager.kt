package com.vest10.peter.madklubandroid.authentication

import android.accounts.Account
import android.accounts.AccountManager
import android.util.Log
import com.vest10.peter.madklubandroid.android.MadklubPreferences
import javax.inject.Inject

/**
 * Created by peter on 9/21/17.
 */
class MadklubUserManager @Inject constructor(
        val accountManager: AccountManager,
        val preferences: MadklubPreferences) {
    var cachedAuthToken: String? = null
    var account: Account? = null

    init {
        val acs = accountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)
        if(acs.isNotEmpty())
            // TODO make selectable through preferences
            account = acs[0]
    }

    fun blockingGetAuthToken(): String {
        Log.d("MadklubNetwork","Hello?!?!?!")
        cachedAuthToken?.let {
            Log.d("MadklubNetwork","Retrieved cached token")
            return it
        }
        // TODO check for empty array, launch account creator activity if so,
        // or maybe verify active account in base activity? Would make sense, since
        // it can be done before any network requests and handle first time launch -> login
        Log.d("MadklubNetwork","Retrieved account $account")
        val authToken = accountManager.blockingGetAuthToken(account,AccountGeneral.AUTHTOKEN_TYPE_USER,false)
        // Caching token for future uses
        cachedAuthToken = authToken
        return authToken
    }

    fun invalidateAuthToken(){
        cachedAuthToken?.let { token ->
            Log.d("MadklubNetwork","Cached token existed, invalidating...")
            account?.let { ac ->
                accountManager.invalidateAuthToken(ac.type,token)
            }
        }
        cachedAuthToken = null
    }
}