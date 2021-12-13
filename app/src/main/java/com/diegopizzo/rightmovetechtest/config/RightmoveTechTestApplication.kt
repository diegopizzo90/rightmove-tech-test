package com.diegopizzo.rightmovetechtest.config

import android.app.Application
import com.diegopizzo.network.creator.creatorModule
import com.diegopizzo.network.interactor.interactorModule
import com.diegopizzo.network.service.retrofitModule
import com.diegopizzo.rightmovetechtest.BuildConfig
import com.diegopizzo.rightmovetechtest.ui.config.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RightmoveTechTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RightmoveTechTestApplication)
            modules(
                retrofitModule(BuildConfig.BASE_URL),
                creatorModule,
                interactorModule,
                viewModelModule
            )
        }
    }
}