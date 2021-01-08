package com.oswaldo.android.koombeatest

import android.app.Application
import com.oswaldo.android.koombeatest.di.repoModule
import com.oswaldo.android.koombeatest.di.viewModelModule
import org.koin.core.context.startKoin

class KApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(repoModule, viewModelModule))
        }
    }
}