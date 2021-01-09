package com.oswaldo.android.koombeatest

import android.app.Application
import android.content.Context
import com.couchbase.lite.CouchbaseLite
import com.oswaldo.android.koombeatest.data.local.DatabaseManager
import com.oswaldo.android.koombeatest.di.repoModule
import com.oswaldo.android.koombeatest.di.viewModelModule
import org.koin.core.context.startKoin

class KApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: KApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(repoModule, viewModelModule))
        }

        val context: Context = KApplication.applicationContext()
        CouchbaseLite.init(this)
    }
}