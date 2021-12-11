package zar1official.simplenote.model.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import zar1official.simplenote.model.entities.NoteItem

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query("SELECT * FROM notes WHERE note_title LIKE :title LIMIT 1")
    suspend fun findNoteByTitle(title: String): NoteItem

    @Query("SELECT * FROM notes WHERE note_text LIKE :text LIMIT 1")
    suspend fun findNoteByText(text: String): NoteItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: NoteItem)

    @Update
    suspend fun updateNote(note: NoteItem)

    @Delete
    suspend fun deleteNote(note: NoteItem)
}