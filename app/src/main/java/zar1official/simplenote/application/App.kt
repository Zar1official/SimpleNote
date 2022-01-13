package zar1official.simplenote.application

import android.app.Application
import androidx.work.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import zar1official.simplenote.data.worker.BackupWorker
import zar1official.simplenote.di.appModule
import zar1official.simplenote.di.dataModule
import zar1official.simplenote.di.domainModule
import java.util.concurrent.TimeUnit

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }
        startWorkManager()
    }

    private fun startWorkManager() {
        WorkManager.getInstance(this).enqueue(
            PeriodicWorkRequest.Builder(
                BackupWorker::class.java, BackupWorker.INTERVAL, TimeUnit.MINUTES
            ).build()
        )
    }
}