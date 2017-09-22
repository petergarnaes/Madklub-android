package com.vest10.peter.madklubandroid.depenedency_injection.modules

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.CustomTypeAdapter
import com.vest10.peter.madklubandroid.authentication.MadklubUserManager
import com.vest10.peter.madklubandroid.networking.NetworkService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import type.CustomType
import javax.inject.Singleton

/**
 * Created by peter on 02-09-17.
 */
@Module
class NetworkingModule {
    companion object {
        val url = "http://10.0.2.2:3000/graphql"
        val csrfToken = "bob"
        val jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImU5MTM4ZTcyLTEyNmEtNDgwZC04NmJiLTM5ZTkxNTUyOGRhNyIsImVtYWlsIjoiMTAwNkB0ZXN0IiwiaWF0IjoxNTA1MjU0MDk0LCJleHAiOjE1MDUzNDA0OTR9.3yhdFowPoFt2TA2gFshEK39hblAtUNWQPBCJj3R2lv8"
    }

    @Provides
    @Singleton
    fun provideHttpClient(userManager: MadklubUserManager): OkHttpClient =
        OkHttpClient().newBuilder()
                .addNetworkInterceptor { chain: Interceptor.Chain? ->
                    Log.d("MadklubNetwork","Starting network request")
                    if(chain != null) {
                        val authToken = userManager.blockingGetAuthToken()
                        val requestBuilder = chain.request().newBuilder()
                                .addHeader("X-CSRF-TOKEN", csrfToken)
                        var cookie = "csrf_token=$csrfToken"
                        if(authToken != null){
                            cookie += ";id_token=$authToken"
                        }
                        requestBuilder.addHeader("Cookie", cookie)
                        chain.proceed(requestBuilder.build())
                    } else {
                        Log.d("MadklubNetwork","Awhat?!?!")
                        chain
                    }
                }
                .build()

    @Provides
    @Singleton
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient = ApolloClient.builder()
                .serverUrl(NetworkingModule.url)
                .okHttpClient(okHttpClient)
                .addCustomTypeAdapter(CustomType.DATE, object : CustomTypeAdapter<DateTime>{
                    override fun decode(value: String?): DateTime =
                        ISODateTimeFormat.dateTimeParser().parseDateTime(value)

                    override fun encode(value: DateTime?): String {
                        val d = DateTime(value).toString()
                        Log.d("Madklub","Parsing date to: $d")
                        return d
                    }
                })
                .build()

    @Provides
    @Singleton
    fun provideNetworkService(
            client: ApolloClient,
            userManager: MadklubUserManager): NetworkService = NetworkService(client,userManager)
}