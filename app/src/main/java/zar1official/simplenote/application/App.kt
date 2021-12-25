package zar1official.simplenote.application

import android.app.Application
import retrofit2.Retrofit
import zar1official.simplenote.model.database.NoteDatabase
import zar1official.simplenote.model.network.Retrofit2Client

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    lateinit var db: NoteDatabase
        private set
    lateinit var retrofitClient: Retrofit
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = NoteDatabase.getDatabase(this)
        retrofitClient = Retrofit2Client.getInstance()
    }
}