package com.arctouch.codechallenge

import android.app.Application
import com.arctouch.codechallenge.base.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(appComponent)
        }
    }
}