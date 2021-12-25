package zar1official.simplenote.model.repositories.base

import kotlinx.coroutines.flow.Flow
import zar1official.simplenote.model.models.Note

interface NoteRepository {
    suspend fun loadNote(): Note
    suspend fun saveNotes(noteModel: Note)
    suspend fun updateNotes(noteModel: Note)
    suspend fun deleteNotes(noteModel: Note)
    fun getNotes(): Flow<List<Note>>
}