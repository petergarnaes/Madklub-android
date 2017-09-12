package com.vest10.peter.madklubandroid.depenedency_injection.modules

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by peter on 02-09-17.
 */
@Module
class NetworkingModule {
    companion object {
        val url = "http://10.0.2.2:3000/graphql"
        val jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImMxMWMxNjk1LTdiN2EtNGUyMi04ODA1LTk3YmFmMWQzYTQ1MiIsImVtYWlsIjoiMTAwNkB0ZXN0IiwiaWF0IjoxNTA1MjEzNDM2LCJleHAiOjE1MDUyOTk4MzZ9.p2ieNNEuz-FkNStJ_H6Q6DvRiAA4GeZCxQv8jA36D6k"
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder()
                .addNetworkInterceptor { chain: Interceptor.Chain? ->
                    if(chain != null) {
                        val request = chain.request().newBuilder()
                                .addHeader("id_token",jwtToken)
                                .build()
                        chain.proceed(request)
                    } else chain
                }
                .build()

    @Provides
    @Singleton
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient = ApolloClient.builder()
                .serverUrl(NetworkingModule.url)
                .okHttpClient(okHttpClient)
                .build()
}