package com.yxhelzvl.umsnh_id

import android.app.Application
import com.yxhelzvl.umsnh_id.core.di.appModule
import com.yxhelzvl.umsnh_id.core.di.dataStoreModule
import com.yxhelzvl.umsnh_id.core.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule, networkModule, dataStoreModule)
        }
    }
}