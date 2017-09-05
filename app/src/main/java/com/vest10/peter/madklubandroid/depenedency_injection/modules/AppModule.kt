package com.vest10.peter.madklubandroid.depenedency_injection.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by peter on 04-09-17.
 */
@Module
class AppModule {
    @Provides
    @Singleton
    fun providesContext(app: Application): Context = app
}