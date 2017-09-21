package com.vest10.peter.madklubandroid.authentication

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by peter on 9/20/17.
 */
class AuthenticationService : Service() {
    override fun onBind(p0: Intent?): IBinder = MadklubAuthenticator(this).iBinder
}