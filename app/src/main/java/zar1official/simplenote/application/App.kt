package zar1official.simplenote.application

import android.app.Application
import zar1official.simplenote.model.dao.NoteDao
import zar1official.simplenote.model.database.NoteDatabase
import zar1official.simplenote.model.repositories.NoteRepositoryImpl
import zar1official.simplenote.model.repositories.base.NoteRepository
import zar1official.simplenote.utils.mappers.NoteMapper

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    lateinit var repository: NoteRepository
        private set

    private lateinit var db: NoteDao

    private lateinit var mapper: NoteMapper

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = NoteDatabase.getDatabase(this).noteDao()
        mapper = NoteMapper()
        repository = NoteRepositoryImpl(db, mapper)
    }
}