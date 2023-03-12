package uk.fernando.tapup

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uk.fernando.tapup.di.KoinModule

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(KoinModule.allModules())
        }
    }
}