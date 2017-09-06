package com.vest10.peter.madklubandroid.main_activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import com.vest10.peter.madklubandroid.R
import com.vest10.peter.madklubandroid.SomeClass
import com.vest10.peter.madklubandroid.networking.NetworkService
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject lateinit var someClass : SomeClass
    @Inject lateinit var client : ApolloClient

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Injected somestring",someClass.toString())
        client.defaultCacheHeaders().toBuilder().addHeader("","").build()

        Rx2Apollo.from(client.query(KitchensQuery.builder().build()))
                .subscribe { res ->
                    val data = res.data()
                    var log = "data is null..."
                    if (data != null) {
                        log = "Have kitchens - "
                        var kitchensString = "null"
                        val kitchens = data.kitchens()
                        if (kitchens != null) {
                            kitchensString = "["
                            for (k in kitchens)
                                kitchensString += k.name() + ","
                            kitchensString += "]"
                        }
                        log += kitchensString
                    }
                    Log.d("Kitchens response", log)
                }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
}
