package com.vest10.peter.madklubandroid.depenedency_injection.components

import com.vest10.peter.madklubandroid.MadklubApplication
import com.vest10.peter.madklubandroid.depenedency_injection.modules.MainActivityModule
import com.vest10.peter.madklubandroid.depenedency_injection.modules.NetworkingModule
import com.vest10.peter.madklubandroid.depenedency_injection.modules.PreferencesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by peter on 02-09-17.
 */

@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class
        , NetworkingModule::class
        , PreferencesModule::class
        , MainActivityModule::class
))
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(myApp: MadklubApplication): Builder
        fun build(): AppComponent
    }
    fun inject(myApp: MadklubApplication)
}