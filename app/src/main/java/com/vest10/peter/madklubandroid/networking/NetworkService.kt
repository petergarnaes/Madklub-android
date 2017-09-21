package com.vest10.peter.madklubandroid.networking

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.rx2.Rx2Apollo
import com.vest10.peter.madklubandroid.authentication.MadklubUserManager
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by peter on 02-09-17.
 *
 * This class provides the abstraction of ApolloClient, account management
 * and refreshing authentication token in case of invalid login.
 */
class NetworkService @Inject constructor(
        val client: ApolloClient,
        val userManager: MadklubUserManager) {

    private fun <T> tryReAuthenticating(getObservable: () -> Observable<Response<T>>) = { t: Throwable ->
        when (t) {
            is ApolloHttpException -> when (t.code()) {
                401,403 -> {
                    // We have invalid auth token, will send user to authentication
                    // page if his account is outdated (hopefully...)
                    Log.d("MadklubNetwork","Had invalid login, trying again...")
                    userManager.invalidateAuthToken()
                    getObservable()
                }
            // Propagate error
                else -> Observable.error(t)
            }
        // Propagate error
            else -> Observable.error(t)
        }
    }

    fun <D: Operation.Data,T,V: Operation.Variables>
            mutation(generateMutation: () -> Mutation<D,T,V>): Observable<Response<T>> {
        fun getObservable() = Rx2Apollo.from(client.mutate(generateMutation()))
        return getObservable().onErrorResumeNext(tryReAuthenticating(::getObservable))
    }

    fun <D: Operation.Data,T,V: Operation.Variables>
            query(generateQuery: () -> Query<D, T, V>): Observable<Response<T>> {
        fun getObservable() = Rx2Apollo.from(client.query(generateQuery()))
        return getObservable().onErrorResumeNext(tryReAuthenticating(::getObservable))
    }
}