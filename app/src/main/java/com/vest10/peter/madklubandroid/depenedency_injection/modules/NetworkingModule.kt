package com.vest10.peter.madklubandroid.depenedency_injection.modules

import android.content.Context
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.CustomTypeAdapter
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.cache.normalized.CacheKey
import com.apollographql.apollo.cache.normalized.CacheKeyResolver
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
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
    }

    @Provides
    @Singleton
    fun provideHttpClient(userManager: MadklubUserManager): OkHttpClient =
        OkHttpClient().newBuilder()
                .addNetworkInterceptor { chain: Interceptor.Chain? ->
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
                        chain
                    }
                }
                .build()

    @Provides
    @Singleton
    fun provideApolloClient(okHttpClient: OkHttpClient,context: Context): ApolloClient {
        val cacheFactory = LruNormalizedCacheFactory(EvictionPolicy.builder().maxSizeBytes(10 * 1024).build())
        val cacheKeyResolver = object : CacheKeyResolver(){
            override fun fromFieldRecordSet(field: ResponseField, recordSet: MutableMap<String, Any>): CacheKey {
                Log.d("Madklub","Record set do we have: ${recordSet["__typename"]}")
                return formatCacheKey(recordSet["__typename"] as String + recordSet["id"] as String)
            }

            override fun fromFieldArguments(field: ResponseField, variables: Operation.Variables): CacheKey {
                Log.d("Madklub","Field arguments do we have: ${field.resolveArgument("__typename",variables)}")
                return formatCacheKey(field.resolveArgument("__typename",variables) as String +
                        field.resolveArgument("id",variables) as String)
            }

            private fun formatCacheKey(id: String?): CacheKey = if(id == null || id.isEmpty())
                CacheKey.NO_KEY
            else
                CacheKey.from(id)
        }
        return ApolloClient.builder()
                .serverUrl(NetworkingModule.url)
                .okHttpClient(okHttpClient)
                .normalizedCache(cacheFactory,cacheKeyResolver)
                .addCustomTypeAdapter(CustomType.DATE, object : CustomTypeAdapter<DateTime> {
                    override fun decode(value: String?): DateTime =
                            ISODateTimeFormat.dateTimeParser().parseDateTime(value)

                    override fun encode(value: DateTime?): String {
                        val d = DateTime(value).toString()
                        Log.d("Madklub", "Parsing date to: $d")
                        return d
                    }
                })
                .build()
    }

        @Provides
        @Singleton
        fun provideNetworkService(
                client: ApolloClient,
                userManager: MadklubUserManager): NetworkService = NetworkService(client,userManager)
}