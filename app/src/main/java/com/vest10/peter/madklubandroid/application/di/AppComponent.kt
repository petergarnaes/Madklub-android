package com.vest10.peter.madklubandroid.application.di

import com.vest10.peter.madklubandroid.application.MadklubApplication
import com.vest10.peter.madklubandroid.depenedency_injection.components.ConfigPersistentComponent
import com.vest10.peter.madklubandroid.depenedency_injection.modules.*
import com.vest10.peter.madklubandroid.depenedency_injection.modules.NetworkingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by peter on 02-09-17.
 */

@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        //ActivityInjectorsModule::class,
        BaseActivityInjectorsModule::class,
        AppModule::class,
        NetworkingModule::class,
        PreferencesModule::class
        ))
interface AppComponent : AndroidInjector<MadklubApplication> {
    @Component.Builder
    interface Builder {
        // With binds instance we can provide MadklubApplication
        @BindsInstance
        fun application(myApp: MadklubApplication): Builder
        fun build(): AppComponent
    }
    fun plus(): ConfigPersistentComponent
    override fun inject(myApp: MadklubApplication)
}