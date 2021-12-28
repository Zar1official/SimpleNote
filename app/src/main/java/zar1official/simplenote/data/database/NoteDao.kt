package zar1official.simplenote.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import zar1official.simplenote.data.database.entities.NoteItem

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM notes WHERE note_title LIKE :title LIMIT 1")
    suspend fun findNoteByTitle(title: String): NoteItem

    @Query("SELECT * FROM notes WHERE note_text LIKE :text LIMIT 1")
    suspend fun findNoteByText(text: String): NoteItem

    @Query("SELECT * FROM notes WHERE note_date LIKE :date LIMIT 1")
    suspend fun findNoteByDate(date: Long): NoteItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: NoteItem)

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun findNoteById(id: Long): NoteItem?

    @Update
    suspend fun updateNote(note: NoteItem)

    @Delete
    suspend fun deleteNote(note: NoteItem)
}