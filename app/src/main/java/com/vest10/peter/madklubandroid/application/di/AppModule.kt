package com.vest10.peter.madklubandroid.application.di

import android.app.Application
import android.content.Context
import com.vest10.peter.madklubandroid.user.AppUserManager
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

    //@Provides
    //@Singleton
    //fun providesUserManager(app: Application): AppUserManager = AppUserManager(app)
}