package zar1official.simplenote.ui.screens.notes.base

import zar1official.simplenote.model.Note

interface NoteListView {
    fun onLoadedDataSuccessfully(data: List<Note>)
    fun onLoadingDataFailed()
    fun openNote(note: Note)
}