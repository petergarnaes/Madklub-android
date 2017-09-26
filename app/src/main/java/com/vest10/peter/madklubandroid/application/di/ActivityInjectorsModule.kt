package com.vest10.peter.madklubandroid.application.di

import com.vest10.peter.madklubandroid.depenedency_injection.components.UserComponent
import com.vest10.peter.madklubandroid.main_activity.MainActivity
import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ActivityScope
import com.vest10.peter.madklubandroid.detail_activity.DetailActivity
import com.vest10.peter.madklubandroid.main_activity.di.MainActivityDependenciesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by peter on 04-09-17.
 */
@Module
abstract class ActivityInjectorsModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(
            // Provides actual objects that MainActivity depends on
            MainActivityDependenciesModule::class
            // Provides injector factory for this fragment, which must of course be provided
            // by the parent activity, since the fragment will ask its parent to provide it
            // an injector behind the scenes, when calling AndroidSupportInjection.inject(this).
            //HomeFragmentInjectorModule::class
    ))
    abstract fun contibutesMainActivityInjector(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contibutesDetailActivityInjector(): DetailActivity
    /*@ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(
            OtherActivityDependenciesModule::class,
            ChildInjectorModule::class
    ))
    abstract fun contibutesOtherActivityInjector(): OtherActivity*/
}