package com.vest10.peter.madklubandroid.depenedency_injection.modules

import com.vest10.peter.madklubandroid.HomeFragmentModule
import com.vest10.peter.madklubandroid.MainActivity
import com.vest10.peter.madklubandroid.MainView
import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

/**
 * Created by peter on 02-09-17.
 */
@Module
abstract class MainActivityModule {
    /*@ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(HomeFragmentModule::class))
    abstract fun contibutesMainActivityInjector(): MainActivity*/

    @ActivityScope
    @Binds
    abstract fun bindsMainActivityToMainView(mainActivity: MainActivity): MainView
}