package com.vest10.peter.madklubandroid.depenedency_injection.modules

import com.vest10.peter.madklubandroid.HomeFragmentInjectorModule
import com.vest10.peter.madklubandroid.MainActivity
import com.vest10.peter.madklubandroid.SomeClass
import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ActivityScope
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Named

/**
 * Created by peter on 04-09-17.
 */
@Module
abstract class InjectorsModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(
            // Provides actual objects that MainActivity depends on
            MainActivityDependenciesModule::class,
            // Provides injector factory for this fragment, which must of course be provided
            // by the parent activity, since the fragment will ask its parent to provide it
            // an injector behind the scenes, when calling AndroidSupportInjection.inject(this).
            HomeFragmentInjectorModule::class))
    abstract fun contibutesMainActivityInjector(): MainActivity

}