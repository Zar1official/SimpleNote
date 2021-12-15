package zar1official.simplenote.application

import android.app.Application
import zar1official.simplenote.model.database.NoteDatabase

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    lateinit var db: NoteDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = NoteDatabase.getDatabase(this)
    }
}