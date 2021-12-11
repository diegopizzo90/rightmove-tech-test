package com.diegopizzo.rightmovetechtest.config

import android.app.Application
import com.diegopizzo.network.config.retrofitModule
import com.diegopizzo.rightmovetechtest.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RightmoveTechTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RightmoveTechTestApplication)
            retrofitModule(BuildConfig.BASE_URL)
        }
    }
}