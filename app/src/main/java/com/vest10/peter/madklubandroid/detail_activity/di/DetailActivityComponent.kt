package com.vest10.peter.madklubandroid.detail_activity.di

import com.vest10.peter.madklubandroid.depenedency_injection.scopes.ActivityScope
import com.vest10.peter.madklubandroid.detail_activity.DetailActivity
import dagger.Subcomponent

/**
 * Created by peter on 27-09-17.
 */
@Subcomponent(modules = arrayOf(DetailActivityDependenciesModule::class))
@ActivityScope
interface DetailActivityComponent {
    fun inject(detailActivity: DetailActivity)
}