package zar1official.simplenote.ui.screens.notes.base

import zar1official.simplenote.model.models.Note

interface NoteListPresenter {
    fun onLoadData()
    fun onAttemptOpenNote(position: Int, notesList: ArrayList<Note>)
}