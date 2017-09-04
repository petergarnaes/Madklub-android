package com.vest10.peter.madklubandroid.depenedency_injection.modules

import com.vest10.peter.madklubandroid.MainActivity
import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by peter on 04-09-17.
 */
@Module
abstract class InjectorsModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contibutesMainActivityInjector(): MainActivity

}