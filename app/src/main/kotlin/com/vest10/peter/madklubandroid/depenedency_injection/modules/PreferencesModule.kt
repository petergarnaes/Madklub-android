package com.vest10.peter.madklubandroid.depenedency_injection.modules

import com.vest10.peter.madklubandroid.application.MadklubApplication
import com.vest10.peter.madklubandroid.android.MadklubPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by peter on 02-09-17.
 */
@Module
class PreferencesModule {
    @Provides
    @Singleton
    fun providePreferences(myApp: MadklubApplication): MadklubPreferences {
        return MadklubPreferences(myApp)
    }
}