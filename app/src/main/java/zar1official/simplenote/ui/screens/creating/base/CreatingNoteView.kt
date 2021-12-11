package zar1official.simplenote.ui.screens.creating.base

import zar1official.simplenote.model.models.Note

interface CreatingNoteView {
    fun saveFailed()
    fun saveEmptyContent()
    fun shareNote(title: String, text: String)
    fun shareFailed()
    fun saveSuccess(note: Note)
}

