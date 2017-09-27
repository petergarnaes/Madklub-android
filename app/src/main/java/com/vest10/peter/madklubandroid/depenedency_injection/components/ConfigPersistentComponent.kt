package com.vest10.peter.madklubandroid.depenedency_injection.components

import com.vest10.peter.madklubandroid.application.di.ActivityInjectorsModule_ContibutesDetailActivityInjector
import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ConfigPersistentScope
import com.vest10.peter.madklubandroid.detail_activity.di.DetailActivityComponent
import com.vest10.peter.madklubandroid.main_activity.di.MainActivityComponent
import dagger.Subcomponent

/**
 * Created by peter on 27-09-17.
 */
@ConfigPersistentScope
@Subcomponent
interface ConfigPersistentComponent {
    // TODO with enough of these, lets do a multibind map
    fun mainActivityComponent(): MainActivityComponent
    fun detailActivityComponent(): DetailActivityComponent
}