package com.vest10.peter.madklubandroid.user

import android.app.Application
import com.vest10.peter.madklubandroid.application.MadklubApplication
import com.vest10.peter.madklubandroid.depenedency_injection.components.UserComponent
import com.vest10.peter.madklubandroid.depenedency_injection.modules.UserModule
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.rxkotlin.toMaybe
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by peter on 18-09-17.
 */
@Singleton
class AppUserManager @Inject constructor(private val application: MadklubApplication,
                                         private val userComponentBuilder: UserComponent.Builder) {
    private var userCache: User? = null

    var userComponent: UserComponent? = null
        private set

    fun loginWithUserName(userName: String): Flowable<User> {
        return userMaybeFromCache
                .concatWith(User(userName).toMaybe())
                .take(1)
                .doOnNext { user -> createUserSession(user) }
    }

    private fun createUserSession(user: User) {
        userComponent = userComponentBuilder.userModule(UserModule(user)).build()
    }


    private val userMaybeFromCache: Maybe<User>
        get() = if (userCache != null) {
            Maybe.just(userCache)
        } else {
            Maybe.empty()
        }

    val isLoggedIn: Boolean
        get() = userComponent != null

    fun logOut() {
        userCache = null
        userComponent = null
    }

    fun attemptToLoadUser() {
        if(!isLoggedIn){
            // TODO Get user from store

            // If no user can be loaded, userComponent should maybe be null?
        }
    }
}