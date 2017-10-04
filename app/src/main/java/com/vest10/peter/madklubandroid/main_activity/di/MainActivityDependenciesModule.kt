package com.vest10.peter.madklubandroid.main_activity.di

import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ActivityScope
import com.vest10.peter.madklubandroid.main_activity.MainActivity
import dagger.Module
import dagger.Provides

/**
 * Created by peter on 02-09-17.
 */
@Module
class MainActivityDependenciesModule(val activity: MainActivity) {
    // So if a module class is abstract, its methods must still be accessible, which is then ny
    // static access. This is not really a Kotlin thing, but this is how you make kotlin and
    // dagger understand each-other
    /*@Module
    companion object {
        @Provides
        @ActivityScope
        @JvmStatic
        fun someClass(): SomeClass {
            return SomeClass()
        }
    }*/
    @Provides
    @ActivityScope
    fun provideMainActivity(): MainActivity = activity

    //@ActivityScope
    //@Provides
    //fun providesMainPresenter(): MainPresenter = MainPresenter()

    /*@ActivityScope
    @Binds
    abstract fun bindsMainActivityToMainView(mainActivity: MainActivity): MainView*/
}