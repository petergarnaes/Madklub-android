package com.vest10.peter.madklubandroid

import com.vest10.peter.madklubandroid.depenedency_injection.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by peter on 02-09-17.
 */
@Module
abstract class HomeFragmentInjectorModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = arrayOf(HomeFragmentDependenciesModule::class))
    abstract fun contibutesHomeFragment(): HomeFragment
}