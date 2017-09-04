package com.vest10.peter.madklubandroid.depenedency_injection.components

import com.vest10.peter.madklubandroid.MainActivity
import com.vest10.peter.madklubandroid.depenedency_injection.modules.MainActivityModule
import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ActivityScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by peter on 04-09-17.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(MainActivityModule::class))
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}