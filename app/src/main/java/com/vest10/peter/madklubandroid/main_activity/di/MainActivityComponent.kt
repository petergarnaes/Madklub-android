package com.vest10.peter.madklubandroid.main_activity.di

import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ActivityScope
import com.vest10.peter.madklubandroid.main_activity.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * Created by peter on 18-09-17.
 */
@Subcomponent(modules = arrayOf(MainActivityDependenciesModule::class))
@ActivityScope
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)

    /*@Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun activity(mainActivity: MainActivity): MainActivityComponent.Builder
        fun build(): MainActivityComponent
    }*/
}