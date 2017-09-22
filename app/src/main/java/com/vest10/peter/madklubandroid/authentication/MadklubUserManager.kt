package com.vest10.peter.madklubandroid.authentication

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.os.Bundle
import android.util.Log
import com.vest10.peter.madklubandroid.android.MadklubPreferences
import com.vest10.peter.madklubandroid.application.BaseActivity
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
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
        setupAccount()
    }

    fun blockingGetAuthToken(): String? {
        Log.d("MadklubNetwork","Hello?!?!?!")
        cachedAuthToken?.let {
            Log.d("MadklubNetwork","Retrieved cached token")
            return it
        }
        Log.d("MadklubNetwork","Retrieved account $account")
        if(account != null) {
            val authToken = accountManager.blockingGetAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_USER, false)
            // Caching token for future uses
            cachedAuthToken = authToken
        }
        return cachedAuthToken
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

    private fun setupAccount(){
        val acs = accountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)
        if(acs.isNotEmpty())
            // TODO make selectable through preferences
            account = acs[0]
    }

    fun setupAuthentication(baseActivity: BaseActivity): Observable<Unit> =
        if(account == null){
            Observable.just(
                    ""
            ).observeOn(Schedulers.io()).doOnNext {
                accountManager.getAuthTokenByFeatures(
                    AccountGeneral.ACCOUNT_TYPE,
                    AccountGeneral.AUTHTOKEN_TYPE_USER,
                    null,
                    baseActivity,
                    null,
                    null,
                    null,
                    null).result.getString(AccountManager.KEY_AUTHTOKEN)
            }.doOnNext {
                cachedAuthToken = it
                setupAccount()
            }.map { }
        } else Observable.just(Unit)
}