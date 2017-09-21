package com.vest10.peter.madklubandroid.application.di

import android.accounts.AccountManager
import android.app.Application
import android.content.Context
import com.vest10.peter.madklubandroid.application.MadklubApplication
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
    fun providesContext(app: MadklubApplication): Context = app

    @Provides
    @Singleton
    fun providesAccountManager(context: Context): AccountManager = AccountManager.get(context)
    //@Provides
    //@Singleton
    //fun providesUserManager(app: Application): AppUserManager = AppUserManager(app)
}