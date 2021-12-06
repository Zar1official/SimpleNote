package zar1official.simplenote.ui.screens.notes.base

import zar1official.simplenote.model.Note

interface NoteListPresenter {
    fun onLoadData()
    fun onAttemptOpenNote(note: Note)
}