package zar1official.simplenote.domain

import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun loadNote(): Note
    suspend fun saveNotes(noteModel: Note)
    suspend fun updateNotes(noteModel: Note)
    suspend fun deleteNotes(noteModel: Note)
    suspend fun findNotes(noteModel: Note): Note?
    fun getNotes(): Flow<List<Note>>
}