package com.vest10.peter.madklubandroid.depenedency_injection.components

import com.vest10.peter.madklubandroid.application.di.ActivityInjectorsModule
import com.vest10.peter.madklubandroid.application.di.AppComponent
import com.vest10.peter.madklubandroid.depenedency_injection.modules.UserModule
import com.vest10.peter.madklubandroid.depenedency_injection.scopes.UserScope
import com.vest10.peter.madklubandroid.main_activity.MainActivity
import com.vest10.peter.madklubandroid.main_activity.di.MainActivityComponent
import com.vest10.peter.madklubandroid.main_activity.di.MainActivityDependenciesModule
import com.vest10.peter.madklubandroid.user.User
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule

/**
 * Created by peter on 17-09-17.
 */
@Subcomponent(
        //dependencies= arrayOf(AppComponent::class),
        modules = arrayOf(
                //AndroidInjectionModule::class,
                //ActivityInjectorsModule::class,
                UserModule::class))
@UserScope
interface UserComponent {
    //fun mainActivityBuilder(): MainActivityComponent.Builder

    @Subcomponent.Builder
    interface Builder {
        fun userModule(userModule: UserModule): Builder
        //@BindsInstance
        //fun user(user: User): Builder
        fun build(): UserComponent
    }

    fun plus(mainActivityModule: MainActivityDependenciesModule): MainActivityComponent
}