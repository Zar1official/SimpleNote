package zar1official.simplenote.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import zar1official.simplenote.model.database.entities.NoteItem

@Database(entities = [NoteItem::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        const val DATABASE_NAME = "note_database"

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context) =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
    }
}