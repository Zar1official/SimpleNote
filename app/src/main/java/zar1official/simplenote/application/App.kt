package zar1official.simplenote.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import zar1official.simplenote.di.appModule
import zar1official.simplenote.di.dataModule
import zar1official.simplenote.di.domainModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}