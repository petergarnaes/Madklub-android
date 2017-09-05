package com.vest10.peter.madklubandroid.home_fragment.di

import com.vest10.peter.madklubandroid.depenedency_injection.scopes.FragmentScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Created by peter on 05-09-17.
 */
@Module
class HomeFragmentDependenciesModule {
    @FragmentScope
    @Provides
    @Named("something")
    fun providesString(): String = "Bob"
}