package com.vest10.peter.madklubandroid.authentication

import LoginMutation
import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloException
import com.vest10.peter.madklubandroid.depenedency_injection.modules.NetworkingModule
import okhttp3.OkHttpClient


/**
 * Created by peter on 9/20/17.
 */
class MadklubAuthenticator(val context: Context) : AbstractAccountAuthenticator(context) {
    val client = ApolloClient.builder()
            .serverUrl(NetworkingModule.url)
            .okHttpClient(OkHttpClient())
            .build()

    // This method provides description for the given label.
    // We only have one type of account, so we only ever have one label
    override fun getAuthTokenLabel(authTokenType: String?): String =
        when (authTokenType) {
            AccountGeneral.AUTHTOKEN_TYPE_USER -> "Regular user access to own account"
            //"readOnly" -> "Read only access to Madklub account"
            else -> authTokenType + " (label)"
        }

    override fun getAuthToken(response: AccountAuthenticatorResponse,
                              account: Account,
                              authTokenType: String,
                              options: Bundle?): Bundle {
        // Check the cached authToken is valid, right now we only have one type :/
        if(authTokenType != AccountGeneral.AUTHTOKEN_TYPE_USER){
            val result = Bundle()
            Log.d("Here?!","!!!")
            result.putString(AccountManager.KEY_ERROR_MESSAGE,"Invalid type of authentication!")
            return result
        }

        val accountManager = AccountManager.get(context)

        var authToken: String? = accountManager.peekAuthToken(account,authTokenType)

        // If accountManager is unsuccessful we try logging in again
        if(authToken == null || authToken.isEmpty()){
            val password = accountManager.getPassword(account)
            try {
                val response = client.mutate(LoginMutation.builder()
                        .email(account.name)
                        .password(password)
                        .build()).execute()
                // Response succeeded withour errors
                if(!response.hasErrors()){
                    authToken = response.data()?.login()?.token()
                } else {
                    // If query had error(s)
                    val result = Bundle()
                    result.putInt(AccountManager.KEY_ERROR_CODE,AccountManager.ERROR_CODE_BAD_REQUEST)
                    result.putString(AccountManager.KEY_ERROR_MESSAGE,"App request has errors in the query...")
                    return result
                }
            } catch (e: ApolloException){
                // Some error, either server is down or somehow unreachable...
                val result = Bundle()
                result.putInt(AccountManager.KEY_ERROR_CODE,AccountManager.ERROR_CODE_NETWORK_ERROR)
                result.putString(AccountManager.KEY_ERROR_MESSAGE,"Server is unreachable...")
                return result
            }
        }

        // If we get an authToken - we return it
        if (authToken != null && authToken.isNotEmpty()) {
            val result = Bundle()
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken)
            return result
        }

        // If logging in was unsuccessful we have wrong credentials or no credentials
        // In either case we launch the AuthenticatorActivity.
        // The reason it is not the server being down
        val intent = Intent(context, AuthenticationActivity::class.java)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        intent.putExtra(AuthenticationActivity.ARG_ACCOUNT_TYPE, account.type)
        intent.putExtra(AuthenticationActivity.ARG_AUTH_TYPE, authTokenType)
        intent.putExtra(AuthenticationActivity.ARG_ACCOUNT_NAME, account.name)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

    override fun hasFeatures(p0: AccountAuthenticatorResponse?, p1: Account?, p2: Array<out String>?): Bundle {
        val result = Bundle()
        result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT,false)
        return result
    }

    override fun addAccount(response: AccountAuthenticatorResponse?,
                            accountType: String?,
                            authTokenType: String?,
                            requiredFeatures: Array<out String>?,
                            options: Bundle?): Bundle {
        Log.d("MadklubAuth","addAccount")
        // Setting up our custom account data
        val intent = Intent(context,AuthenticationActivity::class.java)
        intent.putExtra(AuthenticationActivity.ARG_ACCOUNT_TYPE,accountType)
        intent.putExtra(AuthenticationActivity.ARG_AUTH_TYPE,authTokenType)
        intent.putExtra(AuthenticationActivity.ARG_IS_ADDING_NEW_ACCOUNT,true)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,response)

        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT,intent)
        return bundle
    }

    // Not implemented, we do not use these features
    override fun editProperties(p0: AccountAuthenticatorResponse?, p1: String?): Bundle = Bundle()
    override fun confirmCredentials(p0: AccountAuthenticatorResponse?, p1: Account?, p2: Bundle?): Bundle = Bundle()
    override fun updateCredentials(p0: AccountAuthenticatorResponse?, p1: Account?, p2: String?, p3: Bundle?): Bundle = Bundle()
}