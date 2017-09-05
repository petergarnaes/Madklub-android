package com.vest10.peter.madklubandroid.depenedency_injection.modules

import com.vest10.peter.madklubandroid.application.MadklubApplication
import com.vest10.peter.madklubandroid.networking.NetworkService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by peter on 02-09-17.
 */
@Module
class NetworkingModule {
    @Provides
    @Singleton
    fun provideNetworkingService(myApp: MadklubApplication): NetworkService {
        return NetworkService(myApp)
    }
}