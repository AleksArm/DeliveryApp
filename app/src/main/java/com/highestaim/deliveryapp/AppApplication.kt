package com.highestaim.deliveryapp

import com.highestaim.deliveryapp.DI.appRepositories
import com.highestaim.deliveryapp.DI.appViewModels
import android.app.Application
import com.highestaim.deliveryapp.service.PreferenceService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppApplication)
            androidLogger(Level.DEBUG)
            modules(listOf(appRepositories, appViewModels))
        }

        PreferenceService.get().injectContext(applicationContext)
    }

}