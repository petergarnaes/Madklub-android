package com.vest10.peter.madklubandroid.depenedency_injection.modules

import com.vest10.peter.madklubandroid.depenedency_injection.scopes.UserScope
import com.vest10.peter.madklubandroid.user.User
import dagger.Module
import dagger.Provides

/**
 * Created by peter on 17-09-17.
 */
@Module
class UserModule(val user: User) {
    @Provides
    @UserScope
    fun provideUser(): User = user
}