package com.himelz.shopper

import android.app.Application
import com.himelz.data.di.dataModule
import com.himelz.domain.di.domainModule
import com.himelz.shopper.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShopperApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {

            androidContext(this@ShopperApplication)
            modules(
                listOf(
                    presentationModule,
                    domainModule,
                    dataModule
                )
            )
        }
    }
}