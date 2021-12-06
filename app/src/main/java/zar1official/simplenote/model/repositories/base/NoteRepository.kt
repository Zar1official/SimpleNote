package zar1official.simplenote.model.repositories.base

import zar1official.simplenote.model.Note

interface NoteRepository {
    fun saveNote()
    fun getNotes(): List<Note>?
}