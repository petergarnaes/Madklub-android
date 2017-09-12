package com.vest10.peter.madklubandroid.depenedency_injection.modules

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.CustomTypeAdapter
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.DateTimeFormatterBuilder
import org.joda.time.format.ISODateTimeFormat
import type.CustomType
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

/**
 * Created by peter on 02-09-17.
 */
@Module
class NetworkingModule {
    companion object {
        val url = "http://10.0.2.2:3000/graphql"
        val csrfToken = "bob"
        val jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImMxMWMxNjk1LTdiN2EtNGUyMi04ODA1LTk3YmFmMWQzYTQ1MiIsImVtYWlsIjoiMTAwNkB0ZXN0IiwiaWF0IjoxNTA1MjI4NjI3LCJleHAiOjE1MDUzMTUwMjd9.IYVIU-Q_v0mosD4EB9iGC2FU5S6_BGZZRtjiSK3HV6U"
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder()
                .addNetworkInterceptor { chain: Interceptor.Chain? ->
                    if(chain != null) {
                        val request = chain.request().newBuilder()
                                .addHeader("X-CSRF-TOKEN", csrfToken)
                                .addHeader("Cookie", "id_token=$jwtToken;csrf_token=$csrfToken")
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
                .addCustomTypeAdapter(CustomType.DATE, object : CustomTypeAdapter<Date>{
                    override fun decode(value: String?): Date =
                        ISODateTimeFormat.dateTimeParser().parseDateTime(value).toDate()

                    override fun encode(value: Date?): String {
                        val d = DateTime(value).toString()
                        Log.d("Madklub","Parsing date to: $d")
                        return d
                    }

                })
                .build()
}