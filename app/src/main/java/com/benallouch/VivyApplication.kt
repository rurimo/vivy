package com.benallouch

import android.app.Application
import com.benallouch.vivy.BuildConfig
import com.benallouch.vivy.di.networkModule
import com.benallouch.vivy.di.repositoryModule
import com.benallouch.vivy.di.viewModelModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class VivyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VivyApplication)
            modules(networkModule)
            modules(repositoryModule)
            modules(viewModelModule)
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
    }
}