package com.vest10.peter.madklubandroid.depenedency_injection.modules

import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ActivityScope
import com.vest10.peter.madklubandroid.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by peter on 18-09-17.
 */
@Module
abstract class BaseActivityInjectorsModule {
    @ActivityScope
    @ContributesAndroidInjector
    /*(modules = arrayOf(
            LoginActivityDependenciesModule::class,
    ))*/
    abstract fun contibutesLoginInjector(): LoginActivity
}